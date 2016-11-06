package com.hiddenswitch.proto3.server;

import com.hiddenswitch.proto3.net.common.ClientToServerMessage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SocketServerSession implements Runnable {
	public static final int PORT = 11111;
	public static final String HOST = "127.0.0.1";
	private Lock serverSessionLock = new ReentrantLock();
	private Map<String, ServerGameSession> games = new HashMap<>();
	boolean shouldRun = true;

	public SocketServerSession() {
	}

	public Lock getLock() {
		return serverSessionLock;
	}

	public void run() {
		try {
			ServerSocket s = new ServerSocket(PORT);
			while (true) {
				Socket socket = s.accept();
				SocketClientSession connection = new SocketClientSession(this, socket);
				new Thread(connection).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
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
	
	public GameSession createGameSession(PregamePlayerConfiguration player1, PregamePlayerConfiguration player2){
		ServerGameSession newSession = new ServerGameSession(player1, player2);
		games.put(newSession.getId(), newSession);
		return newSession;
	}
}
