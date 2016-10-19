package com.hiddenswitch.proto3.server;

import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.GameAction;

public interface ServerListener {
	void onPlayerConnected(Player player1, Player player2);

	void onActionRegistered(Player callingPlayer, GameAction action);
}
