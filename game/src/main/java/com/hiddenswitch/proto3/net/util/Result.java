package com.hiddenswitch.proto3.net.util;

import io.vertx.core.AsyncResult;

import java.io.Serializable;

/**
 * Created by bberman on 11/27/16.
 */
public class Result<T> implements AsyncResult<T>, Serializable {
	final T inner;
	final RuntimeException error;
	public Result(RuntimeException error, T result) {
		this.error = error;
		this.inner = result;
	}
	@Override
	public T result() {
		return inner;
	}

	@Override
	public Throwable cause() {
		return error;
	}

	@Override
	public boolean succeeded() {
		return inner != null;
	}

	@Override
	public boolean failed() {
		return inner == null;
	}
}
