package com.hiddenswitch.proto3.client;

import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.GameAction;

public interface SendToServer {
	void registerPlayer(Player player1, Player player2);

	void registerAction(Player callingPlayer, GameAction action);
}
