package com.hiddenswitch.proto3.net.impl.util;

import co.paralleluniverse.fibers.Suspendable;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.ref.WeakReference;

/**
 * Created by bberman on 12/6/16.
 */
public class ActivityMonitor {
	private static Logger logger = LoggerFactory.getLogger(ActivityMonitor.class);
	private final String gameId;
	private final long noActivityTimeout;
	private final WeakReference<Vertx> vertx;
	private long lastTimerId = Long.MIN_VALUE;
	private final Handler<String> onTimeout;

	public ActivityMonitor(Vertx vertx, String gameId, long noActivityTimeout, Handler<String> onTimeout) {
		this.vertx = new WeakReference<>(vertx);
		this.gameId = gameId;
		this.noActivityTimeout = noActivityTimeout;
		this.onTimeout = onTimeout;
	}

	private void handleTimeout(long t) {
		onTimeout.handle(gameId);
	}

	@Suspendable
	public void activity() {
		final Vertx vertx = this.vertx.get();
		if (vertx == null) {
			return;
		}

		cancel();

		lastTimerId = vertx.setTimer(noActivityTimeout, this::handleTimeout);
	}

	@Suspendable
	public void cancel() {
		final Vertx vertx = this.vertx.get();

		if (vertx == null) {
			return;
		}

		if (lastTimerId != Long.MIN_VALUE) {
			vertx.cancelTimer(lastTimerId);
		}
	}
}
