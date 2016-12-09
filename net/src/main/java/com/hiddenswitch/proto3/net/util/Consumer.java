package com.hiddenswitch.proto3.net.util;

import io.vertx.core.Context;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.Message;

import java.io.Serializable;
import java.util.function.Function;

/**
 * Created by bberman on 12/7/16.
 */
public class Consumer {
	public static <T, R> Handler<Message<Buffer>> of(Function<T, R> method) {
		// Get the context at the time of calling this function
		final Context context = Vertx.currentContext();
		return (Message<Buffer> message) -> {
			context.executeBlocking(blocking -> {
				VertxBufferInputStream inputStream = new VertxBufferInputStream(message.body());
				try {
					T request = Serialization.deserialize(inputStream);
					R response = method.apply(request);

					if (response == null) {
						blocking.fail(new NullPointerException());
						return;
					}

					Buffer reply = Buffer.buffer(512);
					Serialization.serialize(response, new VertxBufferOutputStream(reply));
					blocking.complete(reply);
				} catch (Exception e) {
					blocking.fail(e);
				}
			}, false, then -> {
				if (then.succeeded()) {
					message.reply(then.result());
				} else {
					message.fail(1, then.cause().getMessage());
				}
			});
		};
	}
}

