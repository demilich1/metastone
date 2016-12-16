package com.hiddenswitch.proto3.net;

import com.hiddenswitch.proto3.draft.DraftContext;
import com.hiddenswitch.proto3.net.util.AbstractMatchmakingTest;
import io.vertx.ext.unit.TestContext;
import net.demilich.metastone.game.decks.Deck;
import org.junit.Test;

/**
 * Created by bberman on 12/16/16.
 */
public class DraftTest extends AbstractMatchmakingTest {
	@Test
	public void testDraftAndJoin(TestContext context) {
		wrapSync(context, this::createTwoPlayersAndMatchmake);
	}

	@Override
	protected Deck createDeckForMatchmaking(int playerId) {
		DraftContext context = new DraftContext();
		context.accept(done -> {
		});
		return context.getPublicState().createDeck();
	}
}
