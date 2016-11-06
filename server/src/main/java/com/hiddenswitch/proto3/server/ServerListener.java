package com.hiddenswitch.proto3.server;

import java.util.List;

import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.cards.Card;

public interface ServerListener {
	void onPlayerConnected(Player player);

	void onActionRegistered(Player callingPlayer, GameAction action);
	
	void onMulliganReceived(Player player, List<Card> discardedCards);
}
