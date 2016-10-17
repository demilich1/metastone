package com.hiddenswitch.proto3;

import java.util.List;

import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.TurnState;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.events.GameEvent;

public interface RemoteUpdateListener {
	public void onGameEvent(GameEvent event);
	public void onGameEnd(Player winner);
	public void setPlayers(Player localPlayer, Player remotePlayer);
	public void onActivePlayer(Player activePlayer);
	public void onTurnEnd(Player activePlayer, int turnNumber, TurnState turnState);
	public void onUpdate(Player player1, Player player2, TurnState newState);
	public void onRequestAction(List<GameAction> availableActions);
}
