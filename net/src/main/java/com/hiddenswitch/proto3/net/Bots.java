package com.hiddenswitch.proto3.net;

import com.hiddenswitch.proto3.net.models.MulliganRequest;
import com.hiddenswitch.proto3.net.models.MulliganResponse;

import java.util.stream.Collectors;

/**
 * Created by bberman on 12/7/16.
 */
public class Bots extends Service<Bots> {
	@Override
	public void start() {

	}

	public MulliganResponse mulligan(MulliganRequest request) {
		// Reject cards that cost more than 3
		MulliganResponse response = new MulliganResponse();
		response.discardedCards = request.cards.stream().filter(c -> c.getBaseManaCost() > 3).collect(Collectors.toList());
		return response;
	}
}
