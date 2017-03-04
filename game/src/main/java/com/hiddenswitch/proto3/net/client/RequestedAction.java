package com.hiddenswitch.proto3.net.client;

import com.hiddenswitch.proto3.net.common.GameState;
import net.demilich.metastone.game.actions.GameAction;

import java.util.List;

/**
 * Created by bberman on 12/3/16.
 */
public class RequestedAction {
	public final String id;
	public final GameState state;
	public final List<GameAction> availableActions;

	public RequestedAction(String id, GameState state, List<GameAction> availableActions) {
		this.id = id;
		this.state = state;
		this.availableActions = availableActions;
	}
}
