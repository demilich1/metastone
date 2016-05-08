package net.demilich.metastone.gui.autoupdate;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import net.demilich.metastone.BuildConfig;
import net.demilich.metastone.GameNotification;
import net.demilich.nittygrittymvc.SimpleCommand;
import net.demilich.nittygrittymvc.interfaces.INotification;

public class CheckForUpdateCommand extends SimpleCommand<GameNotification> {

	private static Logger logger = LoggerFactory.getLogger(CheckForUpdateCommand	.class);

	private static final String MANIFEST_URL = "http://demilich.net/metastone/version/manifest.json";

	@Override
	public void execute(INotification<GameNotification> notification) {
		new Thread(this::check).start();
	}

	private void check() {
		try {
			RequestConfig globalConfig = RequestConfig.custom().setCircularRedirectsAllowed(true).build();
			CloseableHttpClient httpclient = HttpClientBuilder.create().build();
			logger.debug("Requesting: " + MANIFEST_URL);

			HttpGet httpGet = new HttpGet(MANIFEST_URL);
			httpGet.setConfig(globalConfig);

			CloseableHttpResponse response = httpclient.execute(httpGet);
			try {

				HttpEntity entity = response.getEntity();
				String htmlContent = EntityUtils.toString(entity);
				EntityUtils.consume(entity);
				Gson gson = new Gson();
				VersionInfo versionInfo = gson.fromJson(htmlContent, VersionInfo.class);
				if (versionInfo.isNewerVersionAvailable(BuildConfig.VERSION)) {
					logger.debug("Newer version available: {}" + versionInfo.version);
					getFacade().sendNotification(GameNotification.NEW_VERSION_AVAILABLE, versionInfo);
				} else {
					logger.debug("Version up-to-date");
				}
			} finally {
				response.close();
			}

		} catch (Exception e) {
			logger.warn("Auto updater version check failed: " + e.toString());
		}

	}

}
