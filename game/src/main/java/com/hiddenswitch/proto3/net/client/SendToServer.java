package com.hiddenswitch.proto3.net.client;

import java.util.List;

import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.cards.Card;

public interface SendToServer {
	void registerPlayer(Player player);

	void registerAction(Player callingPlayer, GameAction action);
	
	void sendFirstMessage(Player player, String gameId);
	
	void sendMulligan(Player player, List<Card> discardedCards);
}
