package com.hiddenswitch.proto3.server;

import com.hiddenswitch.proto3.net.common.ClientToServerMessage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SocketServerSession implements Runnable {
	private static final int DEFAULT_PORT = 11111;
	private final int port;
	private Lock serverSessionLock = new ReentrantLock();
	private Map<String, ServerGameSession> games = new HashMap<>();
	private boolean running = true;

	public SocketServerSession() {
		this.port = DEFAULT_PORT;
	}

	public SocketServerSession(int port) {
		this.port = port;
	}

	Lock getLock() {
		return serverSessionLock;
	}

	public void run() {
		try {
			ServerSocket s = new ServerSocket(port);
			while (true) {
				Socket socket = s.accept();
				SocketClientSession connection = new SocketClientSession(this, socket);
				new Thread(connection).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			kill();
		}
	}

	public void kill() {
		setRunning(false);
		getGames().values().forEach(ServerGameSession::kill);
	}

	void onFirstMessage(SocketClientSession clientSession, ClientToServerMessage message) {
		//TODO: check authenticity here;
		Map<String, ServerGameSession> games = getGames();
		if (games.containsKey(message.getGameId())) {
			games.get(message.getGameId()).registerClient(clientSession, message);
		} else {
			ServerGameSession newGame = new ServerGameSession();
			newGame.registerClient(clientSession, message);
			games.put(message.getGameId(), newGame);
		}
	}

	public ServerGameSession createGameSession(PregamePlayerConfiguration player1, PregamePlayerConfiguration player2) {
		// Check if a session already exists for these two players
		ServerGameSession newSession = new ServerGameSession(getHost(), getPort(), player1, player2);
		games.put(newSession.getGameId(), newSession);
		return newSession;
	}

	public int getPort() {
		return port;
	}

	public String getHost() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return "127.0.0.1";
		}
	}

	public Map<String, ServerGameSession> getGames() {
		return Collections.unmodifiableMap(games);
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		if (!running) {
			kill();
		}
	}
}
