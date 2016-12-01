package com.hiddenswitch.proto3.net.models;

public class MatchmakingRequestRetry {
	private String receipt;
	public int delayMilliseconds;

	public MatchmakingRequestRetry(String receipt) {
		setReceipt(receipt);
	}

	public String getReceipt() {
		return receipt;
	}

	private void setReceipt(String receipt) {
		this.receipt = receipt;
	}
}
