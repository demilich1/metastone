package com.hiddenswitch.proto3.net;

import co.paralleluniverse.fibers.Suspendable;
import com.hiddenswitch.proto3.net.models.*;

/**
 * Created by bberman on 12/8/16.
 */
public interface Games {
	@Suspendable
	ContainsGameSessionResponse containsGameSession(ContainsGameSessionRequest request);

	@Suspendable
	CreateGameSessionResponse createGameSession(CreateGameSessionRequest request);

	@Suspendable
	DescribeGameSessionResponse describeGameSession(DescribeGameSessionRequest request);

	@Suspendable
	EndGameSessionResponse endGameSession(EndGameSessionRequest request);
}
