package com.hiddenswitch.proto3.server;

import com.hiddenswitch.proto3.common.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SocketServerSession implements ServerCommunicationReceive, ServerCommunicationSend, Runnable {
	public static final int PORT = 11111;
	private static Lock simpleLock = new ReentrantLock();
	SocketClientSession c1;
	SocketClientSession c2;
	ServerListener listener;
	Socket socketConnection;
	boolean shouldRun = true;

	public SocketServerSession() {
	}

	public Lock getLock() {
		return simpleLock;
	}

	@Override
	public RemoteUpdateListener getPlayerListener(int player) {
		//TODO: FIX player checking
		if (player == 0) {
			return c1;
		} else {
			return c2;
		}
	}

	@Override
	public void registerListener(ServerListener listener) {
		this.listener = listener;
	}

	public void run() {
		try {
			ServerSocket s = new ServerSocket(PORT);
			Socket socket = s.accept();
			c1 = new SocketClientSession(this, socket);
			new Thread(c1).start();

			Socket socket2 = s.accept();
			c2 = new SocketClientSession(this, socket2);
			new Thread(c2).start();


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void kill() {
		shouldRun = false;
	}

}
