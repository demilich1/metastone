package com.hiddenswitch.proto3.net;

import ch.qos.logback.classic.Level;
import com.hiddenswitch.proto3.draft.DraftContext;
import com.hiddenswitch.proto3.net.util.AbstractMatchmakingRuntime;
import io.vertx.ext.unit.TestContext;
import net.demilich.metastone.game.decks.Deck;
import org.junit.Test;

/**
 * Created by bberman on 12/16/16.
 */
public class DraftTest extends AbstractMatchmakingRuntime {
	@Test
	public void testDraftAndJoin(TestContext context) {
		setLoggingLevel(Level.ERROR);
		wrapSync(context, this::createTwoPlayersAndMatchmake);
	}

	@Override
	protected Deck createDeckForMatchmaking(int playerId) {
		setLoggingLevel(Level.ERROR);
		DraftContext context = new DraftContext();
		context.accept(done -> {
		});
		return context.getPublicState().createDeck();
	}
}
