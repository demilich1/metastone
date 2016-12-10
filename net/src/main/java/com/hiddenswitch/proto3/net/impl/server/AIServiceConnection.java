package com.hiddenswitch.proto3.net.impl.server;

import com.hiddenswitch.proto3.net.Bots;
import com.hiddenswitch.proto3.net.common.GameState;
import com.hiddenswitch.proto3.net.common.RemoteUpdateListener;
import com.hiddenswitch.proto3.net.common.ServerGameContext;
import com.hiddenswitch.proto3.net.models.MulliganRequest;
import com.hiddenswitch.proto3.net.models.MulliganResponse;
import com.hiddenswitch.proto3.net.models.RequestActionRequest;
import com.hiddenswitch.proto3.net.models.RequestActionResponse;
import com.hiddenswitch.proto3.net.util.Broker;
import com.hiddenswitch.proto3.net.util.ServiceProxy;
import io.vertx.core.AsyncResult;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.TurnState;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.events.GameEvent;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by bberman on 12/9/16.
 */
public class AIServiceConnection implements RemoteUpdateListener {
	final int playerId;
	final ServiceProxy<Bots> bots;
	final WeakReference<ServerGameContext> context;

	public AIServiceConnection(ServerGameContext context, int playerId) {
		final EventBus eventBus = Vertx.currentContext().owner().eventBus();
		this.bots = Broker.proxy(Bots.class, eventBus);

		this.context = new WeakReference<>(context);
		this.playerId = playerId;
	}

	@Override
	public void onGameEvent(GameEvent event) {
	}

	@Override
	public void onGameEnd(Player winner) {
	}

	@Override
	public void setPlayers(Player localPlayer, Player remotePlayer) {
	}

	@Override
	public void onActivePlayer(Player activePlayer) {
	}

	@Override
	public void onTurnEnd(Player activePlayer, int turnNumber, TurnState turnState) {
	}

	@Override
	public void onUpdate(GameState state) {
	}

	@Override
	public void onRequestAction(String messageId, GameState state, List<GameAction> actions) {
		bots.async((AsyncResult<RequestActionResponse> result) -> {
			final ServerGameContext gc = context.get();
			if (gc == null) {
				return;
			}
			gc.onActionReceived(messageId, result.result().gameAction);
		}).requestAction(new RequestActionRequest(state, playerId, actions));
	}

	@Override
	public void onMulligan(String messageId, Player player, List<Card> cards) {
		bots.async((AsyncResult<MulliganResponse> result) -> {
			final ServerGameContext gc = context.get();
			if (gc == null) {
				return;
			}
			gc.onMulliganReceived(messageId, player, result.result().discardedCards);
		}).mulligan(new MulliganRequest(cards));
	}
}
