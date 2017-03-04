package com.hiddenswitch.proto3.net.common;

import io.vertx.core.AsyncResult;

/**
 * Created by bberman on 11/27/16.
 */
public class NullResult implements AsyncResult<Boolean> {
	public static final NullResult SUCESSS = new NullResult();

	@Override
	public Boolean result() {
		return true;
	}

	@Override
	public Throwable cause() {
		return null;
	}

	@Override
	public boolean succeeded() {
		return true;
	}

	@Override
	public boolean failed() {
		return false;
	}
}
