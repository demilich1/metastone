package com.hiddenswitch.proto3;

import com.hiddenswitch.proto3.server.SocketServerSession;

public class MetaStoneSimpleServer {

	public static void main(String[] args) {
		SocketServerSession ssc = new SocketServerSession();
		new Thread(ssc).start();
		System.out.println("working");
	}

}
