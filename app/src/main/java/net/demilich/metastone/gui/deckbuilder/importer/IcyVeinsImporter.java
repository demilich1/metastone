package net.demilich.metastone.gui.deckbuilder.importer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.decks.Deck;
import net.demilich.metastone.game.entities.heroes.HeroClass;

public class IcyVeinsImporter implements IDeckImporter {

	private static Logger logger = LoggerFactory.getLogger(IcyVeinsImporter.class);

	@Override
	public Deck importFrom(String url) {
		try {
			RequestConfig globalConfig = RequestConfig.custom().setCircularRedirectsAllowed(true).build();
			CloseableHttpClient httpclient = HttpClientBuilder.create().build();
			String exportUrl = url;
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
		
		Document doc = Jsoup.parse(htmlContent);
		
		String deckName = doc.getElementsByClass("page_breadcrumbs_item").last().text();
		Elements cardLines = doc.getElementsByClass("deck_card_list").get(0).getElementsByTag("li");
		
		for (Element e: cardLines){
			if (!e.text().startsWith("1") && !e.text().startsWith("2")) {
				continue;
			}
			int count = Integer.parseInt(String.valueOf(e.text().charAt(0)));
			String cardName = e.getElementsByTag("a").get(0).text();
			for (int i = 0; i < count; i++){
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
			logger.debug("Card added - {}", card.getName());
		}
		if (!deck.isComplete()) {
			logger.error("Deck with name only has {}.", deck.getCards().toList().size());
			return null;
		}
		return deck;
	}

}
