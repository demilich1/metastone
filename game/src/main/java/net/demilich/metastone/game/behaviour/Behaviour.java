package net.demilich.metastone.game.behaviour;

import java.io.Serializable;
import java.util.List;

import co.paralleluniverse.fibers.Suspendable;
import io.vertx.core.Handler;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.GameAction;

public abstract class Behaviour implements IBehaviour, Serializable {

	public IBehaviour clone() {
		try {
			return (IBehaviour) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void onGameOver(GameContext context, int playerId, int winningPlayerId) {
	}

	@Override
	@Suspendable
	public void requestActionAsync(GameContext context, Player player, List<GameAction> validActions, Handler<GameAction> handler) {
		GameAction action = requestAction(context, player, validActions);
		if (handler != null) {
			handler.handle(action);
		}
	}
}
