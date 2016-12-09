package com.hiddenswitch.proto3.net;

import co.paralleluniverse.fibers.Suspendable;
import com.hiddenswitch.proto3.server.PregamePlayerConfiguration;
import com.hiddenswitch.proto3.server.ServerGameSession;

/**
 * Created by bberman on 12/9/16.
 */
public interface GameServer {
	@Suspendable
	void kill(String gameId);

	ServerGameSession createGameSession(PregamePlayerConfiguration player1, PregamePlayerConfiguration player2, String gameId, long noActivityTimeout);
}
