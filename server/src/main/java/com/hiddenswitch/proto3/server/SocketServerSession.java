package com.hiddenswitch.proto3.server;

import com.hiddenswitch.proto3.net.common.ClientToServerMessage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SocketServerSession implements Runnable {
	private static final int DEFAULT_PORT = 11111;
	private final int port;
	private Lock serverSessionLock = new ReentrantLock();
	private Map<String, ServerGameSession> games = new HashMap<>();
	boolean shouldRun = true;

	public SocketServerSession() {
		this.port = DEFAULT_PORT;
	}

	public SocketServerSession(int port) {
		this.port = port;
	}

	public Lock getLock() {
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
		shouldRun = false;
		for (ServerGameSession sgs : games.values()) {
			sgs.kill();
		}
	}

	public void onFirstMessage(SocketClientSession clientSession, ClientToServerMessage message) {
		//TODO: check authenticity here;
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
		games.put(newSession.getId(), newSession);
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
		return games;
	}
}
