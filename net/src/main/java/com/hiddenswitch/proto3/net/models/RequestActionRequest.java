package com.hiddenswitch.proto3.net.models;

import com.hiddenswitch.proto3.net.common.GameState;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.actions.GameAction;

import java.io.Serializable;
import java.util.List;

/**
 * Created by bberman on 12/7/16.
 */
public class RequestActionRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	public GameState gameState;
	public int playerId;
	public List<GameAction> validActions;

	public RequestActionRequest(GameState gameState, int playerId, List<GameAction> validActions) {
		this.gameState = gameState;
		this.playerId = playerId;
		this.validActions = validActions;
	}
}
