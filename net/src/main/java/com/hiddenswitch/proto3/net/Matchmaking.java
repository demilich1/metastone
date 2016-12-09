package com.hiddenswitch.proto3.net;

import com.hiddenswitch.proto3.net.common.MatchmakingRequest;
import com.hiddenswitch.proto3.net.common.MatchmakingResponse;
import com.hiddenswitch.proto3.net.models.MatchExpireRequest;
import com.hiddenswitch.proto3.net.models.MatchExpireResponse;

/**
 * Created by bberman on 12/8/16.
 */
public interface Matchmaking {
	MatchmakingResponse matchmakeAndJoin(MatchmakingRequest matchmakingRequest);

	MatchExpireResponse expireMatch(MatchExpireRequest request);
}
