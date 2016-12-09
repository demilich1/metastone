package com.hiddenswitch.proto3.net;

import co.paralleluniverse.fibers.Suspendable;
import com.hiddenswitch.proto3.net.models.*;

/**
 * Created by bberman on 12/8/16.
 */
public interface Games {
	ContainsGameSessionResponse containsGameSession(ContainsGameSessionRequest request);

	CreateGameSessionResponse createGameSession(CreateGameSessionRequest request);

	DescribeGameSessionResponse describeGameSession(DescribeGameSessionRequest request);

	EndGameSessionResponse endGameSession(EndGameSessionRequest request);
}
