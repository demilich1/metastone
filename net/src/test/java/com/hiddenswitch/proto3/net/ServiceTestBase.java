package com.hiddenswitch.proto3.net;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import net.demilich.metastone.game.cards.CardParseException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.net.URISyntaxException;

@RunWith(VertxUnitRunner.class)
public abstract class ServiceTestBase<T extends Service<T>> {
	Logger logger = LoggerFactory.getLogger(ServiceTestBase.class);
	protected Vertx vertx;
	protected T service;

	public abstract void deployServices(Vertx vertx, Handler<AsyncResult<T>> done);

	@Before
	public void setUp(TestContext context) {
		vertx = Vertx.vertx();
		final Async async = context.async();
		deployServices(vertx, then -> {
			service = then.result();
			vertx.executeBlocking(done -> {
				service.withEmbeddedConfiguration();
				done.complete();
			}, then2 -> {
				logger.info("Embedded configuration completed.");
				context.assertNotNull(service);
				async.complete();
			});
		});
	}

	@After
	public void tearDown(TestContext context) {
		logger.info("Tearing down vertx.");
		vertx.close(context.asyncAssertSuccess());
	}

	protected void wrapBlocking(TestContext context, Runnable code) {
		final Async async = context.async();
		vertx.executeBlocking(fut -> {
			try {
				code.run();
			} catch (Exception e) {
				Assert.fail(e.getMessage());
			}
			fut.complete();
		}, then -> {
			async.complete();
		});
	}
}
