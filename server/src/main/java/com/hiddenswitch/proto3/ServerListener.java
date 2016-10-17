package com.hiddenswitch.proto3;

import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.GameAction;

public interface ServerListener {
	public void onPlayerConnected(Player player1, Player player2);

	public void onActionRegistered(Player callingPlayer, GameAction action);
}
