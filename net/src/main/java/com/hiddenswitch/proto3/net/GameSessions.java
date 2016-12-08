package com.hiddenswitch.proto3.net;

import com.hiddenswitch.proto3.net.models.*;

/**
 * Created by bberman on 12/8/16.
 */
public interface GameSessions {
	ContainsGameSessionResponse containsGameSession(ContainsGameSessionRequest request);

	CreateGameSessionResponse createGameSession(CreateGameSessionRequest request);

	DescribeGameSessionResponse describeGameSession(DescribeGameSessionRequest request);

	EndGameSessionResponse endGameSession(EndGameSessionRequest request);
}
