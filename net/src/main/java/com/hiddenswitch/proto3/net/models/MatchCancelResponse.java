package com.hiddenswitch.proto3.net.models;

import java.io.Serializable;

public class MatchCancelResponse implements Serializable {
	private final boolean isCanceled;

	public MatchCancelResponse(boolean value) {
		this.isCanceled = value;
	}

	public boolean getCanceled() {
		return isCanceled;
	}
}
