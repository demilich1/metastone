package com.hiddenswitch.proto3.net;

import co.paralleluniverse.fibers.Suspendable;
import com.hiddenswitch.proto3.net.models.MatchmakingRequest;
import com.hiddenswitch.proto3.net.models.MatchmakingResponse;
import com.hiddenswitch.proto3.net.util.Serialization;
import io.vertx.core.Future;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.sync.SyncVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.LoggerHandler;

import static io.vertx.ext.sync.Sync.awaitResult;

/**
 * Created by bberman on 11/27/16.
 */
public class AnonymousServices extends SyncVerticle {
	@Override
	@Suspendable
	public void start(Future<Void> started) {
		Logger logger = LoggerFactory.getLogger(AnonymousServices.class);

		HttpServer server = vertx.createHttpServer();
		Router router = Router.router(vertx);

		try {
			GameSessions gameSessions = new GameSessions().withEmbeddedConfiguration();

			String socketServerDeploymentId = awaitResult(done -> {
				vertx.deployVerticle(gameSessions, done);
			});

			logger.info("Deployed gameSessions with verticle ID {}.", socketServerDeploymentId);

			Games games = new Games()
					.withGameSessions(gameSessions)
					.withEmbeddedConfiguration();

			String gamesDeploymentId = awaitResult(done -> {
				vertx.deployVerticle(games, done);
			});

			logger.info("Deployed games with verticle ID {}.", gamesDeploymentId);

			router.route("*").failureHandler(LoggerHandler.create());

			router.route("/v0/anonymous/matchmake")
					.method(HttpMethod.POST)
					.consumes("application/json")
					.produces("application/json")
					.handler(LoggerHandler.create())
					.handler(BodyHandler.create())
					.blockingHandler(routingContext -> {
						MatchmakingRequest request = Serialization.deserialize(routingContext.getBodyAsString(), MatchmakingRequest.class);
						String userId = routingContext.request().getHeader("X-Auth-UserId");
						MatchmakingResponse matchmakingResponse = games.matchmakeAndJoin(request, userId);
						int statusCode = 200;
						if (matchmakingResponse.getRetry() != null) {
							statusCode = 202;
						}
						routingContext.response().setStatusCode(statusCode);
						routingContext.response().end(Serialization.serialize(matchmakingResponse));
					});

			HttpServer listening = awaitResult(done -> server.requestHandler(router::accept).listen(80, done));
			logger.info("Listening on port 80.");
			started.complete();
		} catch (Exception e) {
			started.fail(e);
		}

		server.requestHandler(router::accept).listen(80, o -> {
			if (o.succeeded()) {
				started.complete();
			} else {
				started.fail(o.cause());
			}
		});
	}
}
