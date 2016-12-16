package com.hiddenswitch.proto3.net.models;

import java.io.Serializable;

/**
 * Created by bberman on 12/8/16.
 */
public class ContainsGameSessionResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	public final boolean result;

	public ContainsGameSessionResponse(boolean result) {
		this.result = result;
	}
}
