package com.hiddenswitch.proto3.net.client;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

import com.hiddenswitch.proto3.net.common.ClientToServerMessage;
import com.hiddenswitch.proto3.net.common.RemoteUpdateListener;
import com.hiddenswitch.proto3.net.common.ServerToClientMessage;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.cards.Card;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SocketClientConnection implements ClientCommunicationReceive, ClientCommunicationSend, Runnable {
	private final String host;
	private final int port;
	private BlockingQueue<ClientToServerMessage> queue = new LinkedBlockingDeque<>();
	private RemoteUpdateListener updateListener;
	private boolean shouldRun = true;
	private Logger logger = LoggerFactory.getLogger(SocketClientConnection.class);
	private boolean isGameEnded;

	public SocketClientConnection() {
		this("127.0.0.1", 11111);
	}

	public SocketClientConnection(String host, int port) {
		this.host = host;
		this.port = port;
	}

	@Override
	public SendToServer getSendToServer() {
		return new SendToServer() {

			@Override
			public void sendAction(String id, GameAction action) {
				queue.add(new ClientToServerMessage(id, action));
			}

			@Override
			public void sendMulligan(String id, Player player, List<Card> discardedCards) {
				queue.add(new ClientToServerMessage(id, player, discardedCards));
			}

			@Override
			public void sendGenericMessage(ClientToServerMessage message) {
				queue.add(message);
			}
		};
	}

	@Override
	public void RegisterListener(RemoteUpdateListener remoteUpdateListener) {
		this.updateListener = remoteUpdateListener;
	}

	@Override
	public void run() {
		Socket socket = null;
		int retries = 0;
		while (socket == null
				&& retries < 5) {
			try {
				socket = new Socket(getHost(), getPort());
				break;
			} catch (IOException e) {
				logger.warn("Retrying connection to {} : {}", getHost(), getPort());
			}
			retries += 1;
			try {
				Thread.sleep(2000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		if (socket == null) {
			throw new NullPointerException("Could not create socket after 5 retries.");
		}

		try {
			// ReadThread
			final Socket readSocket = socket;
			new Thread(() -> {
				ObjectInputStream serverInputStream = null;
				try {
					InputStream inputStream = readSocket.getInputStream();
					while (shouldRun) {
						// Skip the header for now.
						inputStream.read();
						inputStream.read();
						inputStream.read();
						inputStream.read();
						inputStream.read();
						inputStream.read();
						inputStream.read();
						inputStream.read();
						serverInputStream = new ObjectInputStream(inputStream);

						ServerToClientMessage message = (ServerToClientMessage) serverInputStream.readObject();
						logger.debug("Client received message from server: {}", message);
						switch (message.mt) {
							case ON_GAME_EVENT:
								updateListener.onGameEvent(message.event);
								break;
							case ON_GAME_END:
								isGameEnded = true;
								shouldRun = false;
								updateListener.onGameEnd(message.winner);
								break;
							case SET_PLAYERS:
								updateListener.setPlayers(message.localPlayer, message.remotePlayer);
								break;
							case ON_ACTIVE_PLAYER:
								updateListener.onActivePlayer(message.activePlayer);
								break;
							case ON_UPDATE:
								updateListener.onUpdate(message.gameState);
								break;
							case ON_TURN_END:
								updateListener.onTurnEnd(message.activePlayer, message.turnNumber, message.turnState);
								break;
							case ON_REQUEST_ACTION:
								updateListener.onRequestAction(message.id, message.gameState, message.actions);
								break;
							case ON_MULLIGAN:
								updateListener.onMulligan(message.id, message.player1, message.startingCards);
								break;
							default:
								System.err.println("Unexpected message from server received");
								break;
						}
					}
				} catch (EOFException e) {
					logger.error("The client has been disconnected from the server.");
				} catch (IOException e) {
					if (!isGameEnded) {
						logger.error("The client's read thread experiences an IOException.", e);
					}
				} catch (ClassNotFoundException e) {
					logger.error("The client attempted to deserialize a class that didn't exist.", e);
				} finally {
					if (serverInputStream != null) {
						try {
							serverInputStream.close();
						} catch (IOException e) {
							if (!isGameEnded) {
								logger.warn("The client could not close the server input stream.", e);
							}
						}
					}

					try {
						if (!readSocket.isClosed()) {
							readSocket.close();
						}
					} catch (IOException e) {
						if (!isGameEnded) {
							logger.warn("The client could not close its socket.", e);
						}
					}
				}
			}).start();

			//Write Thread
			final Socket writeSocket = socket;
			new Thread(() -> {
				try {
					DataOutputStream network = new DataOutputStream(new BufferedOutputStream(writeSocket.getOutputStream(), 24000));
					while (shouldRun) {
						ByteArrayOutputStream bytes = new ByteArrayOutputStream();
						ObjectOutputStream objectOutputStream = new ObjectOutputStream(bytes);
						ClientToServerMessage message = queue.take();
						objectOutputStream.writeObject(message);
						objectOutputStream.flush();
						// Write the header to help the server know how much data it is expecting.
						network.write(new byte[]{1, 2, -127, 83});
						int messageSize = bytes.size();
						network.write(ByteBuffer.allocate(4).putInt(messageSize).array());
						network.write(bytes.toByteArray());
						network.flush();
						logger.debug("Client sent message: {}", message);
					}
					network.close();
				} catch (SocketException e) {
					if (!isGameEnded) {
						logger.error("Socket error before the game was ended.", e);
					}
				} catch (IOException e1) {
					logger.error("The write thread has experienced an IOException.", e1);
				} catch (InterruptedException e2) {
					logger.info("Networking thread interrupted. Indicates deliberate disconnection.");
				} finally {
					try {
						if (!writeSocket.isClosed()) {
							writeSocket.close();
						}
					} catch (IOException e) {
						logger.warn("The client could not close its socket.", e);
					}
				}
			}).start();


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void kill() {
		shouldRun = false;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}
}
