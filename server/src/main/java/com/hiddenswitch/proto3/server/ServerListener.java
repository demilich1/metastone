package com.hiddenswitch.proto3.server;

import java.util.List;

import co.paralleluniverse.fibers.Suspendable;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.cards.Card;

public interface ServerListener {
	@Suspendable
	void onPlayerConnected(Player player);

	@Suspendable
	void onActionReceived(String id, Player callingPlayer, GameAction action);
	
	void onMulliganReceived(String id, Player player, List<Card> discardedCards);
}
