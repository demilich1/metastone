package com.hiddenswitch.proto3.net.messages;

public class EndGameMessage extends NetworkMessage {
	private EndGameMessage() {
		setMessageType(MessageType.END_GAME);
	}
}
