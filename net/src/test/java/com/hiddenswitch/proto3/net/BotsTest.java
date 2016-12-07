package com.hiddenswitch.proto3.net;

import com.hiddenswitch.proto3.net.models.MulliganRequest;
import com.hiddenswitch.proto3.net.util.Result;
import com.hiddenswitch.proto3.net.util.ServiceTestBase;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import net.demilich.metastone.game.cards.CardCatalogue;
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

	@Override
	public void deployServices(Vertx vertx, Handler<AsyncResult<Bots>> done) {
		Bots instance = new Bots();
		vertx.deployVerticle(instance, then -> {
			done.handle(new Result<Bots>(instance));
		});
	}
}