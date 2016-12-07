package com.hiddenswitch.proto3.net;

import com.hiddenswitch.proto3.net.common.GameState;
import com.hiddenswitch.proto3.net.models.MulliganRequest;
import com.hiddenswitch.proto3.net.util.Result;
import com.hiddenswitch.proto3.net.util.ServiceTestBase;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.actions.ActionType;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.tests.DebugContext;
import net.demilich.metastone.tests.TestBase;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

/**
 * Created by bberman on 12/7/16.
 */
@RunWith(VertxUnitRunner.class)
public class BotsTest extends ServiceTestBase<Bots> {
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

	@Override
	public void deployServices(Vertx vertx, Handler<AsyncResult<Bots>> done) {
		Bots instance = new Bots();
		vertx.deployVerticle(instance, then -> {
			done.handle(new Result<Bots>(instance));
		});
	}
}