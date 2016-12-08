package com.hiddenswitch.proto3.net.models;

import java.io.Serializable;

/**
 * Created by bberman on 12/8/16.
 */
public class ContainsGameSessionRequest implements Serializable {
	public String gameId;

	public ContainsGameSessionRequest(String gameId) {
		this.gameId = gameId;
	}
}
