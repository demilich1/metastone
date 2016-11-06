package com.hiddenswitch.proto3.net.models;

public abstract class NetworkMessage {
	private MessageType messageType;
	private int order;

	protected NetworkMessage() {
		setMessageType(MessageType.NONE);
	}

	protected NetworkMessage(int order) {
		setMessageType(MessageType.NONE);
		setOrder(order);
	}

	protected void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}

	public MessageType getMessageType() {
		return messageType;
	}

	public int getOrder() {
		return order;
	}

	protected void setOrder(int order) {
		this.order = order;
	}
}
