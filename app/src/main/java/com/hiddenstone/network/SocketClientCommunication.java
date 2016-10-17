package com.hiddenstone.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.GameAction;

public class SocketClientCommunication implements ClientCommunicationReceive, ClientCommunicationSend, Runnable {
	Socket socketConnection;
	public BlockingQueue<ClientToServerMessage> queue = new LinkedBlockingQueue<>();
	RemoteUpdateListener rul;
	boolean shouldRun = true;
	
	@Override
	public SendToServer getSendToServer() {
		return new SendToServer(){
			@Override
			public void registerPlayer(Player player1, Player player2) {
				queue.add(new ClientToServerMessage(player1, player2));
			}

			@Override
			public void registerAction(Player callingPlayer, GameAction action) {
				queue.add(new ClientToServerMessage(callingPlayer, action));
			}
		};
	}

	@Override
	public void RegisterListener(RemoteUpdateListener remoteUpdateListener) {
		this.rul = remoteUpdateListener;
	}

	@Override
	public void run() {
		try{
			Socket socket = new Socket("127.0.0.1", 11111);
			//ReadThread
			new Thread(new Runnable(){
				@Override
				public void run() {
					try{
						ObjectInputStream serverInputStream = new ObjectInputStream(socket.getInputStream());
						while (shouldRun){
							ServerToClientMessage message = (ServerToClientMessage) serverInputStream.readObject();
							switch (message.mt){
							case onGameEvent:
								rul.onGameEvent(message.event);
								break;
							case onGameEnd:
								rul.onGameEnd(message.winner);
								break;
							case setPlayers:
								rul.setPlayers(message.localPlayer, message.remotePlayer);
								break;
							case onActivePlayer:
								rul.onActivePlayer(message.activePlayer);
								break;
							case onUpdate:
								rul.onUpdate(message.player1, message.player2, message.turnState);
								break;
							case onTurnEnd:
								rul.onTurnEnd(message.activePlayer, message.turnNumber, message.turnState);
								break;
							case onRequestAction:
								rul.onRequestAction(message.actions);
								break;
							default:
								System.err.println("Unexpected message from server received");
								break;
							}
						}
						serverInputStream.close();
						socket.close();
					} catch (IOException | ClassNotFoundException e){
						e.printStackTrace();
					}
				}
			}).start();
			
			//Write Thread
			new Thread(new Runnable(){

				@Override
				public void run() {
					try {
						ObjectOutputStream serverOutputStream = new ObjectOutputStream(socket.getOutputStream());
						while (shouldRun) {
							ClientToServerMessage message = queue.take();
							serverOutputStream.writeObject(message);
							serverOutputStream.flush();
							serverOutputStream.reset();
						}
						serverOutputStream.close();
					} catch (IOException | InterruptedException e) {
						e.printStackTrace();
					}

				}
			}).start();
			
			
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void kill(){
		shouldRun = false;
	}
	
	

}
