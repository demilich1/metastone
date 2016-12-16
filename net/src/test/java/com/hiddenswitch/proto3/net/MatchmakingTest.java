package com.hiddenswitch.proto3.net;

import co.paralleluniverse.strands.Strand;
import com.hiddenswitch.proto3.net.models.MatchExpireRequest;
import com.hiddenswitch.proto3.net.util.AbstractMatchmakingTest;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(VertxUnitRunner.class)
public class MatchmakingTest extends AbstractMatchmakingTest {
	@Test
	public void testMatchmakeAndJoin(TestContext context) {
		wrapSync(context, this::createTwoPlayersAndMatchmake);
	}

	@Test
	public void testMatchmakeSamePlayersTwice(TestContext context) {
		wrapSync(context, () -> {
			// Creates the same two players
			String gameId = createTwoPlayersAndMatchmake();
			Strand.sleep(1000L);
			getContext().assertNull(gameSessions.getGameSession(gameId));
			final MatchExpireRequest request = new MatchExpireRequest(gameId);
			getContext().assertFalse(service.expireMatch(request).expired, "We should fail to expire an already expired match.");
			createTwoPlayersAndMatchmake();
		});

	}

}