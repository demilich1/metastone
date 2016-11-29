package com.hiddenswitch.proto3.net.util;

import io.vertx.core.AsyncResult;

import java.io.Serializable;

/**
 * Created by bberman on 11/27/16.
 */
public class Result<T> implements AsyncResult<T>, Serializable {
	final T inner;
	final Throwable error;
	public Result(Throwable error, T result) {
		this.error = error;
		this.inner = result;
	}
	public Result(T result) {
		this.error = null;
		this.inner = result;
	}
	public Result(Throwable error) {
		this.error = error;
		this.inner = null;
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
