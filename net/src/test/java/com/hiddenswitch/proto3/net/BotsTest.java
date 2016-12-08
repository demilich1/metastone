package com.hiddenswitch.proto3.net;

import com.hiddenswitch.proto3.net.common.GameState;
import com.hiddenswitch.proto3.net.impl.BotsImpl;
import com.hiddenswitch.proto3.net.models.MulliganRequest;
import com.hiddenswitch.proto3.net.models.MulliganResponse;
import com.hiddenswitch.proto3.net.models.RequestActionRequest;
import com.hiddenswitch.proto3.net.models.RequestActionResponse;
import com.hiddenswitch.proto3.net.util.AsyncProxy;
import com.hiddenswitch.proto3.net.util.Broker;
import com.hiddenswitch.proto3.net.util.Result;
import com.hiddenswitch.proto3.net.util.ServiceTestBase;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import net.demilich.metastone.game.actions.ActionType;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.CardParseException;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.tests.DebugContext;
import net.demilich.metastone.tests.TestBase;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by bberman on 12/7/16.
 */
@RunWith(VertxUnitRunner.class)
public class BotsTest extends ServiceTestBase<BotsImpl> {
	@Test
	public void testMulligan() throws Exception {
		CardCatalogue.loadCardsFromPackage();
		MulliganRequest request = new MulliganRequest();
		request.cards = new ArrayList<>();
		request.cards.add(CardCatalogue.getCardById("spell_fireball"));
		request.cards.add(CardCatalogue.getCardById("spell_arcane_missiles"));
		request.cards.add(CardCatalogue.getCardById("spell_assassinate"));
		getContext().assertTrue(service.mulligan(request).discardedCards.size() == 2);
	}

	@Test
	public void testRequestAction() throws Exception {
		DebugContext context = TestBase.createContext(HeroClass.HUNTER, HeroClass.PALADIN);
		context.endTurn();
		context.forceStartTurn(context.getActivePlayerId());
		int startTurn = context.getTurn();
		GameAction gameAction = null;
		while (gameAction == null
				|| gameAction.getActionType() != ActionType.END_TURN) {
			RequestActionRequest requestActionRequest = new RequestActionRequest();
			requestActionRequest.gameState = new GameState(context);
			requestActionRequest.playerId = context.getActivePlayerId();
			requestActionRequest.validActions = context.getValidActions();
			RequestActionResponse response = service.requestAction(requestActionRequest);
			gameAction = response.gameAction;
			getContext().assertNotNull(gameAction);
			context.getLogic().performGameAction(context.getActivePlayerId(), gameAction);
		}
		getContext().assertTrue(context.getTurn() > startTurn);
	}

	@Test
	public void testBroker(TestContext context) throws CardParseException, IOException, URISyntaxException {
		CardCatalogue.loadCardsFromPackage();
		final Async async = context.async();
		AsyncProxy<Bots> bots = Broker.proxy(Bots.class, vertx.eventBus());
		final MulliganRequest request = new MulliganRequest();
		request.cards = Arrays.asList(CardCatalogue.getCardById("spell_fireball"), CardCatalogue.getCardById("spell_arcane_missiles"), CardCatalogue.getCardById("spell_assassinate"));
		bots.async((AsyncResult<MulliganResponse> t) -> {
			context.assertTrue(t.result().discardedCards.size() == 2);
			async.complete();
		}).mulligan(request);
	}

	@Override
	public void deployServices(Vertx vertx, Handler<AsyncResult<BotsImpl>> done) {
		BotsImpl instance = new BotsImpl();
		vertx.deployVerticle(instance, then -> {
			done.handle(new Result<BotsImpl>(instance));
		});
	}
}