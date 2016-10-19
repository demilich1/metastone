package com.hiddenswitch.proto3.common;

import java.util.List;

import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.TurnState;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.events.GameEvent;

public interface RemoteUpdateListener {
	void onGameEvent(GameEvent event);

	void onGameEnd(Player winner);

	void setPlayers(Player localPlayer, Player remotePlayer);

	void onActivePlayer(Player activePlayer);

	void onTurnEnd(Player activePlayer, int turnNumber, TurnState turnState);

	void onUpdate(Player player1, Player player2, TurnState newState);

	void onRequestAction(List<GameAction> availableActions);
}
