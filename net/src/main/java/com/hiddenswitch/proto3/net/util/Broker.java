package com.hiddenswitch.proto3.net.util;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.EventBus;

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
			eb.consumer(methodName, Consumer.of(arg -> {
				try {
					return method.invoke(instance, arg);
				} catch (IllegalAccessException | InvocationTargetException ignored) {
					return null;
				}
			}));
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> AsyncProxy<T> proxy(Class<? extends T> serviceInterface, final EventBus bus) {
		final VertxInvocationHandler<T> invocationHandler = new VertxInvocationHandler<>();

		AsyncProxy<T> result = new AsyncProxy<>((T) Proxy.newProxyInstance(
				serviceInterface.getClassLoader(),
				new Class[]{serviceInterface},
				invocationHandler
		));

		invocationHandler.eb = bus;
		invocationHandler.name = serviceInterface.getName();
		invocationHandler.asyncProxy = result;

		return result;
	}

	private static class VertxInvocationHandler<T> implements InvocationHandler {
		AsyncProxy<T> asyncProxy;
		String name;
		EventBus eb;

		@Override
		@SuppressWarnings("unchecked")
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			if (asyncProxy.next == null) {
				throw new RuntimeException();
			}

			if (eb == null) {
				throw new RuntimeException();
			}

			final Handler<AsyncResult<?>> next = asyncProxy.next;
			asyncProxy.next = null;

			Buffer result = Buffer.buffer(512);
			Serialization.serialize(args[0], new VertxBufferOutputStream(result));

			eb.send(name + "::" + method.getName(), result, reply -> {
				if (reply.succeeded()) {
					try {
						Object body = Serialization.deserialize(new VertxBufferInputStream((Buffer) reply.result().body()));
						next.handle(new Result<>(null, body));
					} catch (IOException | ClassNotFoundException e) {
						next.handle(new Result(e));
					}
				} else {
					next.handle(new Result(reply.cause()));
				}
			});

			return null;
		}
	}
}
