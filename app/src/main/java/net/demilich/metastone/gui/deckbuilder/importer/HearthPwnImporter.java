package net.demilich.metastone.gui.deckbuilder.importer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.decks.Deck;
import net.demilich.metastone.game.entities.heroes.HeroClass;

public class HearthPwnImporter implements IDeckImporter {

	private static Logger logger = LoggerFactory.getLogger(HearthPwnImporter.class);

	private String extractId(String url) {
		String result = "";
		boolean digitEncountered = false;
		for (int i = 0; i < url.length(); i++) {
			char c = url.charAt(i);
			if (Character.isDigit(c)) {
				result += c;
				digitEncountered = true;
			} else if (digitEncountered) {
				break;
			}
		}
		return result;
	}

	private String getExportUrl(String url) {
		String idString = extractId(url);
		String result = "http://www.hearthpwn.com/decks/{}/export/2".replace("{}", idString);
		return result;
	}

	@Override
	public Deck importFrom(String url) {
		try {
			RequestConfig globalConfig = RequestConfig.custom().setCircularRedirectsAllowed(true).build();
			CloseableHttpClient httpclient = HttpClientBuilder.create().build();
			String exportUrl = getExportUrl(url);
			logger.debug("Requesting: " + exportUrl);

			HttpGet httpGet = new HttpGet(exportUrl);
			httpGet.setConfig(globalConfig);

			CloseableHttpResponse response = httpclient.execute(httpGet);

			try {

				HttpEntity entity = response.getEntity();
				String htmlContent = EntityUtils.toString(entity);
				EntityUtils.consume(entity);
				return parse(htmlContent);
			} finally {
				response.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	private Deck parse(String htmlContent) {
		List<Card> cards = new ArrayList<Card>();
		HeroClass heroClass = HeroClass.ANY;
		// remove html tags
		htmlContent = htmlContent.replaceAll("\\<.+?\\>", "");
		// remove BBCode tags
		htmlContent = htmlContent.replaceAll("\\[.+?\\]", "");
		// remove empty lines
		htmlContent = htmlContent.replaceAll("(?m)^\\s+", "");
		// unescape
		htmlContent = StringEscapeUtils.unescapeHtml4(htmlContent);
		String lines[] = htmlContent.split("\\r?\\n");
		String deckName = lines[0];
		for (String line : lines) {
			if (!line.startsWith("1") && !line.startsWith("2")) {
				continue;
			}
			int count = Integer.parseInt(String.valueOf(line.charAt(0)));
			String cardName = line.substring(4);
			for (int i = 0; i < count; i++) {
				Card card = CardCatalogue.getCardByName(cardName);
				if (card != null) {
					cards.add(card);
					if (card.getHeroClass() != HeroClass.ANY) {
						heroClass = card.getHeroClass();
					}
				} else {
					logger.error("Card with name {} could not be found", cardName);
				}
			}
		}
		Deck deck = new Deck(heroClass);
		deck.setName(deckName);
		for (Card card : cards) {
			deck.getCards().add(card);
		}
		if (!deck.isComplete()) {
			return null;
		}
		return deck;
	}

}
