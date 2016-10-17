package com.hiddenstone.network;

import java.io.Serializable;

import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.GameAction;

public class ClientToServerMessage implements Serializable {

	public MessageType mt;
	public Player player1;
	public Player player2;
	public Player callingPlayer;
	public GameAction action;
	
	
	public ClientToServerMessage(Player player, GameAction action){
		this.callingPlayer = player;
		this.action = action;
		mt = MessageType.updateAction;
	}
	
	public ClientToServerMessage(Player p1, Player p2){
		this.player1 = p1;
		this.player2 = p2;
		mt = MessageType.registerPlayer;
	}
}
