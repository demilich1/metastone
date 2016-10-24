package com.hiddenswitch.proto3.net.client;
import com.hiddenswitch.proto3.net.common.RemoteUpdateListener;
public interface ClientCommunicationReceive {
	void RegisterListener(RemoteUpdateListener remoteUpdateListener);
}
