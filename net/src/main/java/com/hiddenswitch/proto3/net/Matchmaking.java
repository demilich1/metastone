package com.hiddenswitch.proto3.net;

import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.fibers.Suspendable;
import com.hiddenswitch.proto3.net.common.MatchmakingRequest;
import com.hiddenswitch.proto3.net.common.MatchmakingResponse;
import com.hiddenswitch.proto3.net.models.MatchCancelRequest;
import com.hiddenswitch.proto3.net.models.MatchCancelResponse;
import com.hiddenswitch.proto3.net.models.MatchExpireRequest;
import com.hiddenswitch.proto3.net.models.MatchExpireResponse;

/**
 * Created by bberman on 12/8/16.
 */
public interface Matchmaking {
	@Suspendable
	MatchmakingResponse matchmakeAndJoin(MatchmakingRequest matchmakingRequest) throws InterruptedException, SuspendExecution;

	MatchExpireResponse expireMatch(MatchExpireRequest request);

	MatchCancelResponse cancel(MatchCancelRequest matchCancelRequest);
}
