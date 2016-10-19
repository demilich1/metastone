package com.hiddenswitch.proto3.server;

import com.hiddenswitch.proto3.server.ServerListener;

public interface ServerCommunicationReceive {
	void registerListener(ServerListener listener);
}
