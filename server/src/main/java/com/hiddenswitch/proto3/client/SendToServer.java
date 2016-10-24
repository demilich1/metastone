package com.hiddenswitch.proto3.client;

import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.GameAction;

public interface SendToServer {
	void registerPlayer(Player player);

	void registerAction(Player callingPlayer, GameAction action);
	
	void sendFirstMessage(Player player, String gameId);
}
