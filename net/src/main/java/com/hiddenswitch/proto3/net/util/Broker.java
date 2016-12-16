package com.hiddenswitch.proto3.net.util;

import co.paralleluniverse.fibers.Suspendable;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.EventBus;
import io.vertx.ext.sync.Sync;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by bberman on 12/7/16.
 */
public class Broker {
	public static <T, R extends T> void of(R instance, Class<T> serviceInterface, final EventBus eb) {
		final String name = serviceInterface.getName();

		for (Method method : serviceInterface.getDeclaredMethods()) {
			String methodName = name + "::" + method.getName();

			eb.consumer(methodName, Sync.fiberHandler(Consumer.of(arg -> {
				try {
					return method.invoke(instance, arg);
				} catch (InvocationTargetException e) {
					RuntimeException re = (RuntimeException)(e.getTargetException());
					if (re != null) {
						throw re;
					}
					return null;
				} catch (IllegalAccessException e) {
					return null;
				} catch (Throwable e) {
					throw e;
				}
			})));
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> ServiceProxy<T> proxy(Class<? extends T> serviceInterface, final EventBus bus) {
		final VertxInvocationHandler<T> invocationHandler = new VertxInvocationHandler<>();

		ServiceProxy<T> result = new ServiceProxy<>((T) Proxy.newProxyInstance(
				serviceInterface.getClassLoader(),
				new Class[]{serviceInterface},
				invocationHandler
		));

		invocationHandler.eb = bus;
		invocationHandler.name = serviceInterface.getName();
		invocationHandler.serviceProxy = result;

		return result;
	}

	private static class VertxInvocationHandler<T> implements InvocationHandler {
		ServiceProxy<T> serviceProxy;
		String name;
		EventBus eb;

		@Override
		@Suspendable
		@SuppressWarnings("unchecked")
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			final boolean sync = serviceProxy.sync;
			final Handler<AsyncResult<Object>> next = serviceProxy.next;

			serviceProxy.next = null;
			serviceProxy.sync = false;

			final String methodName = method.getName();

			if (next == null
					&& !sync) {
				throw new RuntimeException();
			}

			if (eb == null) {
				throw new RuntimeException();
			}

			if (sync) {
				return Sync.awaitResult(done -> {
					call(methodName, args, done);
				});
			} else {
				call(methodName, args, next);
				return null;
			}
		}

		@Suspendable
		private void call(String methodName, Object[] args, Handler<AsyncResult<Object>> next) {
			Buffer result = Buffer.buffer(512);

			try {
				Serialization.serialize(args[0], new VertxBufferOutputStream(result));
			} catch (IOException e) {
				next.handle(new Result<>(e));
				return;
			}

			eb.send(name + "::" + methodName, result, Sync.fiberHandler(reply -> {
				if (reply.succeeded()) {
					try {
						Object body = Serialization.deserialize(new VertxBufferInputStream((Buffer) reply.result().body()));
						next.handle(new Result<>(null, body));
					} catch (IOException | ClassNotFoundException e) {
						next.handle(new Result<>(e));
					}
				} else {
					next.handle(new Result<>(reply.cause()));
				}
			}));
		}
	}
}
