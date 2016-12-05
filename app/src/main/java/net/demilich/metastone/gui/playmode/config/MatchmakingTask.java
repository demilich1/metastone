package net.demilich.metastone.gui.playmode.config;

import com.hiddenswitch.proto3.net.common.ClientConnectionConfiguration;
import com.hiddenswitch.proto3.net.common.MatchmakingRequest;
import com.hiddenswitch.proto3.net.common.MatchmakingResponse;
import com.hiddenswitch.proto3.net.util.Serialization;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import javafx.concurrent.Task;
import net.demilich.metastone.BuildConfig;
import net.demilich.metastone.game.decks.Deck;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by bberman on 12/2/16.
 */
class MatchmakingTask extends Task<Void> {
	private final Deck deck;
	private ClientConnectionConfiguration connection;
	private final String userId;
	private final AtomicBoolean isMatchmaking;

	public MatchmakingTask(String sessionId, Deck deck) {
		this.userId = sessionId;
		this.deck = deck;
		this.isMatchmaking = new AtomicBoolean();
	}

	public void stop() {
		isMatchmaking.set(false);
	}

	@Override
	protected Void call() throws Exception {
		Logger logger = LoggerFactory.getLogger(MatchmakingTask.class);
		this.isMatchmaking.set(true);
		try {
			// First request
			MatchmakingRequest request = new MatchmakingRequest();
			request.deck = deck;

			MatchmakingResponse response = getMatchmakingResponse(request);

			while (isMatchmaking.get()
					&& response.getConnection() == null) {
				logger.debug("Retrying multiplayer connection...");
				Thread.sleep(2000);
				request = response.getRetry();
				response = getMatchmakingResponse(request);
			}

			if (!isMatchmaking.get()
					&& (response.getConnection() == null)) {
				logger.debug("Canceling matchmaking.");
				cancelMatchmaking();
			} else {
				logger.debug("Matchmaking successful!");
				connection = response.getConnection();
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}

		return null;
	}

	private MatchmakingResponse getMatchmakingResponse(MatchmakingRequest request) throws IOException {
		CloseableHttpClient client = HttpClientBuilder.create().build();
		RequestConfig globalConfig = RequestConfig.custom().setCircularRedirectsAllowed(true).build();
		HttpPost post1 = new HttpPost(BuildConfig.MATCHMAKING_URI);
		post1.setEntity(new StringEntity(Serialization.serialize(request), ContentType.APPLICATION_JSON));
		post1.addHeader("X-Auth-UserId", userId);
		post1.setConfig(globalConfig);
		HttpPost post = post1;

		CloseableHttpResponse httpResponse = client.execute(post);

		HttpEntity entity = httpResponse.getEntity();
		MatchmakingResponse response = Serialization.deserialize(EntityUtils.toString(entity), MatchmakingResponse.class);
		EntityUtils.consume(entity);
		httpResponse.close();
		client.close();
		return response;
	}

	private void cancelMatchmaking() throws IOException {
		CloseableHttpClient client = HttpClientBuilder.create().build();
		RequestConfig globalConfig = RequestConfig.custom().setCircularRedirectsAllowed(true).build();
		HttpDelete post1 = new HttpDelete(BuildConfig.MATCHMAKING_URI);
		post1.addHeader("X-Auth-UserId", userId);
		post1.setConfig(globalConfig);
		CloseableHttpResponse httpResponse = client.execute(post1);
		httpResponse.close();
		client.close();
	}

	public ClientConnectionConfiguration getConnection() {
		return connection;
	}
}
