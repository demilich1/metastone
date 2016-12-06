package com.hiddenswitch.proto3.net;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public abstract class ServiceTestBase<T extends Service<T>> {
	public static TestContext getContext() {
		return new Assert();
	}

	private static TestContext wrappedContext;
	Logger logger = LoggerFactory.getLogger(ServiceTestBase.class);
	protected Vertx vertx;
	protected T service;

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

//	@After
//	public void tearDown(TestContext context) {
//		logger.info("Tearing down vertx.");
//		vertx.close(context.asyncAssertSuccess());
//	}

	protected void wrapBlocking(TestContext context, Runnable code) {
		ServiceTestBase.wrappedContext = context;
		final Async async = context.async();
		vertx.executeBlocking(fut -> {
			try {
				code.run();
			} catch (Exception e) {
				context.fail(e);
			}
			fut.complete();
		}, then -> {
			async.complete();
			ServiceTestBase.wrappedContext = null;
		});
	}

	private static class Assert implements TestContext {
		@Override
		public <T> T get(String key) {
			return wrappedContext.get(key);
		}

		@Override
		public <T> T put(String key, Object value) {
			return wrappedContext.put(key, value);
		}

		@Override
		public <T> T remove(String key) {
			return wrappedContext.remove(key);
		}

		@Override
		public TestContext assertNull(Object expected) {
			if (wrappedContext == null) {
				org.junit.Assert.assertNull(expected);
			} else {
				wrappedContext.assertNull(expected);
			}
			return this;
		}

		@Override
		public TestContext assertNull(Object expected, String message) {
			if (wrappedContext == null) {
				org.junit.Assert.assertNull(message, expected);
			} else {
				wrappedContext.assertNull(expected, message);
			}
			return this;
		}

		@Override
		public TestContext assertNotNull(Object expected) {
			if (wrappedContext == null) {
				org.junit.Assert.assertNotNull(expected);
			} else {
				wrappedContext.assertNotNull(expected);
			}
			return this;
		}

		@Override
		public TestContext assertNotNull(Object expected, String message) {
			if (wrappedContext == null) {
				org.junit.Assert.assertNotNull(message, expected);
			} else {
				wrappedContext.assertNotNull(expected, message);
			}
			return this;
		}

		@Override
		public TestContext assertTrue(boolean condition) {
			if (wrappedContext == null) {
				org.junit.Assert.assertTrue(condition);
			} else {
				wrappedContext.assertTrue(condition);
			}
			return this;
		}

		@Override
		public TestContext assertTrue(boolean condition, String message) {
			if (wrappedContext == null) {
				org.junit.Assert.assertTrue(message, condition);
			} else {
				wrappedContext.assertTrue(condition, message);
			}
			return this;
		}

		@Override
		public TestContext assertFalse(boolean condition) {
			if (wrappedContext == null) {
				org.junit.Assert.assertFalse(condition);
			} else {
				wrappedContext.assertFalse(condition);
			}
			return this;
		}

		@Override
		public TestContext assertFalse(boolean condition, String message) {
			if (wrappedContext == null) {
				org.junit.Assert.assertFalse(message, condition);
			} else {
				wrappedContext.assertFalse(condition, message);
			}
			return this;
		}

		@Override
		public TestContext assertEquals(Object expected, Object actual) {
			if (wrappedContext == null) {
				org.junit.Assert.assertEquals(expected, actual);
			} else {
				wrappedContext.assertEquals(expected, actual);
			}
			return this;
		}

		@Override
		public TestContext assertEquals(Object expected, Object actual, String message) {
			if (wrappedContext == null) {
				org.junit.Assert.assertEquals(message, expected, actual);
			} else {
				wrappedContext.assertEquals(expected, actual, message);
			}
			return this;
		}

		@Override
		public TestContext assertInRange(double expected, double actual, double delta) {
			if (wrappedContext == null) {
				throw new RuntimeException("Unsupported!");
			} else {
				wrappedContext.assertInRange(expected, actual, delta);
			}
			return this;
		}

		@Override
		public TestContext assertInRange(double expected, double actual, double delta, String message) {
			if (wrappedContext == null) {
				throw new RuntimeException("Unsupported!");
			} else {
				wrappedContext.assertInRange(expected, actual, delta, message);
			}
			return this;
		}

		@Override
		public TestContext assertNotEquals(Object first, Object second) {
			if (wrappedContext == null) {
				org.junit.Assert.assertNotEquals(first, second);
			} else {
				wrappedContext.assertNotEquals(first, second);
			}
			return this;
		}

		@Override
		public TestContext assertNotEquals(Object first, Object second, String message) {
			if (wrappedContext == null) {
				org.junit.Assert.assertEquals(message, first, second);
			} else {
				wrappedContext.assertEquals(first, second, message);
			}
			return this;
		}

		@Override
		public void fail() {
			if (wrappedContext == null) {
				org.junit.Assert.fail("(No message)");
			} else {
				wrappedContext.fail();
			}
		}

		@Override
		public void fail(String message) {
			if (wrappedContext == null) {
				org.junit.Assert.fail(message);
			} else {
				wrappedContext.fail(message);
			}
		}

		@Override
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
}
