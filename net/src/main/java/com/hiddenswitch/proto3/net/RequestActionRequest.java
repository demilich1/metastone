package com.hiddenswitch.proto3.net;

import com.hiddenswitch.proto3.net.common.GameState;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.actions.GameAction;

import java.util.List;

/**
 * Created by bberman on 12/7/16.
 */
public class RequestActionRequest {
	public GameState gameState;
	public int playerId;
	public List<GameAction> validActions;
}
