package com.hiddenswitch.proto3.net.util;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * Created by bberman on 12/8/16.
 */
public class AsyncProxy<T> {
	private final T proxy;
	Handler next;

	AsyncProxy(T proxy) {
		this.proxy = proxy;
	}

	public <R> T async(Handler<AsyncResult<R>> handler) {
		next = handler;
		return proxy;
	}
}
