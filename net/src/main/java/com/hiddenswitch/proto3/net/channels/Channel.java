package com.hiddenswitch.proto3.net.channels;

import com.hiddenswitch.proto3.net.messages.NetworkMessage;

public abstract class Channel implements Runnable {
	private String channelId;

	public Channel(String channelId) {
		setChannelId(channelId);
	}

	@Override
	public abstract void run();

	public abstract boolean connect();

	public abstract NetworkMessage receiveMessage();

	public abstract void sendMessage(NetworkMessage message);

	public String getChannelId() {
		return channelId;
	}

	public abstract int getMessageCount();

	protected void setChannelId(String channelId) {
		this.channelId = channelId;
	}
}
