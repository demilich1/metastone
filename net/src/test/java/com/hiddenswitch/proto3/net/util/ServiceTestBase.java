package com.hiddenswitch.proto3.net.util;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.fibers.FiberScheduler;
import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.fibers.Suspendable;
import co.paralleluniverse.strands.SuspendableRunnable;
import com.hiddenswitch.proto3.net.Service;
import io.vertx.core.*;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.sync.Sync;
import io.vertx.ext.sync.SyncVerticle;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.CardParseException;
import org.junit.Before;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.net.URISyntaxException;

@RunWith(VertxUnitRunner.class)
public abstract class ServiceTestBase<T extends Service<T>> {
	public static TestContext getContext() {
		return new Assert();
	}

	private static TestContext wrappedContext;
	Logger logger = LoggerFactory.getLogger(ServiceTestBase.class);
	protected Vertx vertx;
	protected T service;

	@Before
	public void loadCards(TestContext context) {
		try {
			CardCatalogue.loadCardsFromPackage();
		} catch (IOException | URISyntaxException | CardParseException e) {
			context.fail(e);
		}
		context.async().complete();
	}

	public abstract void deployServices(Vertx vertx, Handler<AsyncResult<T>> done);

	@Before
	public void setUp(TestContext context) {
		vertx = Vertx.vertx();
		final Async async = context.async();
		if (service == null) {
			deployServices(vertx, then -> {
				service = then.result();
				vertx.executeBlocking(done -> {
					if (isEmbeddedServiceRequired()) {
						service.withEmbeddedConfiguration();
					}
					done.complete();
				}, then2 -> {
					if (isEmbeddedServiceRequired()) {
						logger.info("Embedded configuration completed.");
					}
					context.assertNotNull(service);
					async.complete();
				});
			});
		} else {
			async.complete();
		}
	}

	public boolean isEmbeddedServiceRequired() {
		return false;
	}

	@Suspendable
	protected void wrapSync(TestContext context, SuspendableRunnable code) {
		ServiceTestBase.wrappedContext = context;
		final Async async = context.async();

		// Create a verticle on the fly to run sync stuff in, then tear down the verticle
		TestSyncVerticle testVerticle = new TestSyncVerticle(code);
		vertx.deployVerticle(testVerticle, getContext().asyncAssertSuccess(fut -> {
			vertx.undeploy(fut, then -> {
				ServiceTestBase.wrappedContext = null;
				async.complete();
			});
		}));
	}

	private static class Assert implements TestContext {
		@Override
		@Suspendable
		public <T> T get(String key) {
			return wrappedContext.get(key);
		}

		@Override
		@Suspendable
		public <T> T put(String key, Object value) {
			return wrappedContext.put(key, value);
		}

		@Override
		@Suspendable
		public <T> T remove(String key) {
			return wrappedContext.remove(key);
		}

		@Override
		@Suspendable
		public TestContext assertNull(Object expected) {
			if (wrappedContext == null) {
				org.junit.Assert.assertNull(expected);
			} else {
				wrappedContext.assertNull(expected);
			}
			return this;
		}

		@Override
		@Suspendable
		public TestContext assertNull(Object expected, String message) {
			if (wrappedContext == null) {
				org.junit.Assert.assertNull(message, expected);
			} else {
				wrappedContext.assertNull(expected, message);
			}
			return this;
		}

		@Override
		@Suspendable
		public TestContext assertNotNull(Object expected) {
			if (wrappedContext == null) {
				org.junit.Assert.assertNotNull(expected);
			} else {
				wrappedContext.assertNotNull(expected);
			}
			return this;
		}

		@Override
		@Suspendable
		public TestContext assertNotNull(Object expected, String message) {
			if (wrappedContext == null) {
				org.junit.Assert.assertNotNull(message, expected);
			} else {
				wrappedContext.assertNotNull(expected, message);
			}
			return this;
		}

		@Override
		@Suspendable
		public TestContext assertTrue(boolean condition) {
			if (wrappedContext == null) {
				org.junit.Assert.assertTrue(condition);
			} else {
				wrappedContext.assertTrue(condition);
			}
			return this;
		}

		@Override
		@Suspendable
		public TestContext assertTrue(boolean condition, String message) {
			if (wrappedContext == null) {
				org.junit.Assert.assertTrue(message, condition);
			} else {
				wrappedContext.assertTrue(condition, message);
			}
			return this;
		}

		@Override
		@Suspendable
		public TestContext assertFalse(boolean condition) {
			if (wrappedContext == null) {
				org.junit.Assert.assertFalse(condition);
			} else {
				wrappedContext.assertFalse(condition);
			}
			return this;
		}

		@Override
		@Suspendable
		public TestContext assertFalse(boolean condition, String message) {
			if (wrappedContext == null) {
				org.junit.Assert.assertFalse(message, condition);
			} else {
				wrappedContext.assertFalse(condition, message);
			}
			return this;
		}

		@Override
		@Suspendable
		public TestContext assertEquals(Object expected, Object actual) {
			if (wrappedContext == null) {
				org.junit.Assert.assertEquals(expected, actual);
			} else {
				wrappedContext.assertEquals(expected, actual);
			}
			return this;
		}

		@Override
		@Suspendable
		public TestContext assertEquals(Object expected, Object actual, String message) {
			if (wrappedContext == null) {
				org.junit.Assert.assertEquals(message, expected, actual);
			} else {
				wrappedContext.assertEquals(expected, actual, message);
			}
			return this;
		}

		@Override
		@Suspendable
		public TestContext assertInRange(double expected, double actual, double delta) {
			if (wrappedContext == null) {
				throw new RuntimeException("Unsupported!");
			} else {
				wrappedContext.assertInRange(expected, actual, delta);
			}
			return this;
		}

		@Override
		@Suspendable
		public TestContext assertInRange(double expected, double actual, double delta, String message) {
			if (wrappedContext == null) {
				throw new RuntimeException("Unsupported!");
			} else {
				wrappedContext.assertInRange(expected, actual, delta, message);
			}
			return this;
		}

		@Override
		@Suspendable
		public TestContext assertNotEquals(Object first, Object second) {
			if (wrappedContext == null) {
				org.junit.Assert.assertNotEquals(first, second);
			} else {
				wrappedContext.assertNotEquals(first, second);
			}
			return this;
		}

		@Override
		@Suspendable
		public TestContext assertNotEquals(Object first, Object second, String message) {
			if (wrappedContext == null) {
				org.junit.Assert.assertEquals(message, first, second);
			} else {
				wrappedContext.assertEquals(first, second, message);
			}
			return this;
		}

		@Override
		@Suspendable
		public void fail() {
			if (wrappedContext == null) {
				org.junit.Assert.fail("(No message)");
			} else {
				wrappedContext.fail();
			}
		}

		@Override
		@Suspendable
		public void fail(String message) {
			if (wrappedContext == null) {
				org.junit.Assert.fail(message);
			} else {
				wrappedContext.fail(message);
			}
		}

		@Override
		@Suspendable
		public void fail(Throwable cause) {
			if (wrappedContext == null) {
				org.junit.Assert.fail(cause.getMessage());
			} else {
				wrappedContext.fail(cause);
			}
		}

		@Override
		public Async async() {
			return wrappedContext.async();
		}

		@Override
		public Async async(int count) {
			return wrappedContext.async(count);
		}

		@Override
		public <T> Handler<AsyncResult<T>> asyncAssertSuccess() {
			return wrappedContext.asyncAssertSuccess();
		}

		@Override
		public <T> Handler<AsyncResult<T>> asyncAssertSuccess(Handler<T> resultHandler) {
			return wrappedContext.asyncAssertSuccess(resultHandler);
		}

		@Override
		public <T> Handler<AsyncResult<T>> asyncAssertFailure() {
			return wrappedContext.asyncAssertFailure();
		}

		@Override
		public <T> Handler<AsyncResult<T>> asyncAssertFailure(Handler<Throwable> causeHandler) {
			return wrappedContext.asyncAssertFailure(causeHandler);
		}

		@Override
		public Handler<Throwable> exceptionHandler() {
			return wrappedContext.exceptionHandler();
		}
	}

	private static class TestSyncVerticle extends SyncVerticle {
		private final SuspendableRunnable code;

		public TestSyncVerticle(SuspendableRunnable code) {
			this.code = code;
		}

		@Override
		@Suspendable
		public void start() throws SuspendExecution, InterruptedException {
			code.run();
		}
	}
}
