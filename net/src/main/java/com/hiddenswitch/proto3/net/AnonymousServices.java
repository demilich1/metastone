package com.hiddenswitch.proto3.net;

import co.paralleluniverse.fibers.Suspendable;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.hiddenswitch.proto3.net.models.MatchmakingRequest;
import com.hiddenswitch.proto3.net.models.MatchmakingResponse;
import com.hiddenswitch.proto3.net.util.Serialization;
import com.hiddenswitch.proto3.server.GameSession;
import com.hiddenswitch.proto3.server.SocketServer;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;

import static io.vertx.ext.sync.Sync.*;

import io.vertx.core.json.Json;
import io.vertx.ext.sync.SyncVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

/**
 * Created by bberman on 11/27/16.
 */
public class AnonymousServices extends SyncVerticle {
	@Override
	@Suspendable
	public void start(Future<Void> started) {
		HttpServer server = vertx.createHttpServer();
		Router router = Router.router(vertx);

		router.route("/v0/anonymous/matchmake")
				.method(HttpMethod.HEAD)
				.handler(routingContext -> {
					// HEAD: Lookup the retry ID quickly.
				})
				.method(HttpMethod.POST)
				.handler(BodyHandler.create())
				.blockingHandler(routingContext -> {
					HttpServerResponse response = routingContext.response();
					MatchmakingRequest request = Serialization.deserialize(routingContext.getBodyAsString(), MatchmakingRequest.class);

				});

		try {
			GameSessions gameSessions = new GameSessions();
			String socketServerDeploymentId = awaitResult(done -> {
				vertx.deployVerticle(gameSessions, done);
			});

			Games games = new Games().withGameSessions(gameSessions);

			String gamesDeploymentId = awaitResult(done -> {
				vertx.deployVerticle(games, done);
			});

			HttpServer listening = awaitResult(done -> server.requestHandler(router::accept).listen(80, done));
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
