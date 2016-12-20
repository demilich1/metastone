package com.hiddenswitch.proto3.net;

import ch.qos.logback.classic.Level;
import co.paralleluniverse.strands.Strand;
import com.hiddenswitch.proto3.net.models.MatchExpireRequest;
import com.hiddenswitch.proto3.net.util.AbstractMatchmakingRuntime;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(VertxUnitRunner.class)
public class MatchmakingTest extends AbstractMatchmakingRuntime {
	@Test
	public void testMatchmakeAndJoin(TestContext context) {
		setLoggingLevel(Level.ERROR);
		wrapSync(context, this::createTwoPlayersAndMatchmake);
	}

	@Test
	public void testMatchmakeSamePlayersTwice(TestContext context) {
		setLoggingLevel(Level.ERROR);
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