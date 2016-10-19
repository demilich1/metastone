package com.hiddenswitch.proto3;

import com.hiddenswitch.proto3.server.ServerCommunicationReceive;
import com.hiddenswitch.proto3.server.SocketServerListener;
import com.hiddenswitch.proto3.server.SocketServerSession;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MetaStoneSimpleServer {
	public static Lock simpleLock = new ReentrantLock();

	public static void main(String[] args) {

		SocketServerSession ssc = new SocketServerSession();
		ServerCommunicationReceive scr = ssc;
		new Thread(ssc).start();
		System.out.println("working");
		scr.registerListener(new SocketServerListener(ssc));
	}

}
