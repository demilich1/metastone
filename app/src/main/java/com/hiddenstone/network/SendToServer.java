package com.hiddenstone.network;

import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.GameAction;

public interface SendToServer {
	public void registerPlayer(Player player1, Player player2);
	public void registerAction(Player callingPlayer, GameAction action);
}
