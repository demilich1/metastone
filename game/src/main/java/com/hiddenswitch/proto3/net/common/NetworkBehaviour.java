package com.hiddenswitch.proto3.net.common;

import co.paralleluniverse.fibers.Suspendable;
import io.vertx.core.Handler;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.behaviour.Behaviour;
import net.demilich.metastone.game.behaviour.IBehaviour;
import net.demilich.metastone.game.cards.Card;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;

public class NetworkBehaviour extends Behaviour implements Serializable {
	private static final long serialVersionUID = 1L;

	private Logger logger = LoggerFactory.getLogger(NetworkBehaviour.class);
	private IBehaviour wrapBehaviour;

	public NetworkBehaviour(IBehaviour wrapBehaviour) {
		this.wrapBehaviour = wrapBehaviour;
	}

	@Override
	public String getName() {
		return getWrapBehaviour().getName();
	}

	@Override
	public List<Card> mulligan(GameContext context, Player player, List<Card> cards) {
		logger.debug("Requesting mulligan from wrapped behaviour. Player: {}, cards: {}", player, cards);
		return getWrapBehaviour().mulligan(context, player, cards);
	}

	public void mulligan(ServerGameContext context, Player player, List<Card> cards, Handler<List<Card>> handler) {
		logger.debug("Requesting mulligan from network. Player: {}, cards: {}", player, cards);
		context.networkRequestMulligan(player, cards, handler::handle);
	}

	@Override
	public GameAction requestAction(GameContext context, Player player, List<GameAction> validActions) {
		logger.debug("Requesting action from wrapped behaviour. Player: {}, validActions: {}", player, validActions);
		return getWrapBehaviour().requestAction(context, player, validActions);
	}

	@Suspendable
	public void requestActionAsync(ServerGameContext context, Player player, List<GameAction> validActions, Handler<GameAction> handler) {
		logger.debug("Requesting action from network. Player: {}, validActions: {}", player, validActions);
		context.networkRequestAction(new GameState(context), player.getId(), validActions, handler::handle);
	}


	@Override
	public void onGameOver(GameContext context, int playerId, int winningPlayerId) {
		getWrapBehaviour().onGameOver(context, playerId, winningPlayerId);
	}

	public void onGameOverAsync(ServerGameContext context, int playerId, int winningPlayerId) {
		if (winningPlayerId != -1) {
			context.sendGameOver(context.getPlayer(playerId), context.getPlayer(winningPlayerId));
		} else {
			context.sendGameOver(context.getPlayer(playerId), null);
		}
	}

	public IBehaviour getWrapBehaviour() {
		return wrapBehaviour;
	}
}
