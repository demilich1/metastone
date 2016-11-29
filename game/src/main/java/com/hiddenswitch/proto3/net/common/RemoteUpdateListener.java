package com.hiddenswitch.proto3.net.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

import net.demilich.metastone.game.Environment;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.TurnState;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.events.GameEvent;

public interface RemoteUpdateListener {
	void onGameEvent(GameEvent event);

	void onGameEnd(Player winner);

	void setPlayers(Player localPlayer, Player remotePlayer);

	void onActivePlayer(Player activePlayer);

	void onTurnEnd(Player activePlayer, int turnNumber, TurnState turnState);

	void onUpdate(GameState state);

	void onRequestAction(String messageId, List<GameAction> availableActions);
	
	void onMulligan(String messageId, Player player, List<Card> cards);
}

