package com.hiddenswitch.proto3.net.util;

import co.paralleluniverse.fibers.Suspendable;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * A proxy of a Vertx event bus communicating service.
 *
 * @param <T> The service class proxied.
 */
public class ServiceProxy<T> {
	private final T proxy;
	Handler next;
	boolean sync;

	ServiceProxy(T proxy) {
		this.proxy = proxy;
	}

	/**
	 * Retrieves a proxy of the service configured for async calls. Pass the handler for the result first. The methods
	 * called on the proxy will <b>not</b> return their values, they will return null. The call will go across the event
	 * bus and arrive to your handler.
	 * <p>
	 * You cannot reuse this proxy for more than one method call. You must always call sync() or async() for the
	 * subsequent usage of the proxy.
	 *
	 * @param handler The handler of the async call. Fully specify the handler to get the right type for the method you
	 *                subsequently call on the proxy.
	 * @param <R>     The return type of the method you will call on the proxy. This is typically a Response object.
	 * @return A proxy whose methods will return null.
	 */
	public <R> T async(Handler<AsyncResult<R>> handler) {
		next = handler;
		sync = false;
		return proxy;
	}

	/**
	 * Retrieves a proxy of the service configured for Fibers "sync" calls. You must be inside a Fiber (e.g., a handler
	 * wrapped in Sync.fiberHandler) to use this proxy. The methods called on the proxy will return their values, even
	 * though the call with go across the event bus.
	 * <p>
	 * You cannot reuse this proxy for more than one method call. You must always call sync() or async() for the
	 * subsequent usage of the proxy.
	 *
	 * @return {T} A proxy whose methods will return the actual values.
	 */
	@Suspendable
	public T sync() {
		next = null;
		sync = true;
		return proxy;
	}
}
