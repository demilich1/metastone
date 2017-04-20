package net.demilich.metastone.gui.deckbuilder.importer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.decks.Deck;
import net.demilich.metastone.game.entities.heroes.HeroClass;

public class HearthHeadImporter implements IDeckImporter {
	private static Logger logger = LoggerFactory.getLogger(IcyVeinsImporter.class);

	@Override
	public Deck importFrom(String url) {
		String exportUrl = url;
		try {
			return parse(exportUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private List<String> getCardIds(Document doc){
		Elements metas = doc.getElementsByTag("meta"); 
		String title = "";
		String cards = "";
		for (int i = 0; i < metas.size(); i++) { 
			if (metas.get(i).attr("property").equals("x-hearthstone:deck:cards")) { 
				cards = metas.get(i).attr("content"); 
			} 
			if (metas.get(i).attr("property").equals("x-hearthstone:deck")) { 
				title = metas.get(i).attr("content"); 
			} 
		}
		List<String> cs = new ArrayList<String>();
		cs.add(0, title);
		List<String> cids = Arrays.asList(cards.split(","));
		for (String c: cids){
			cs.add(c);
		}
		return cs;
	}

	private Deck parse(String url) throws IOException {
		List<Card> cards = new ArrayList<Card>();
		HeroClass heroClass = HeroClass.ANY;
		
		Response response= Jsoup.connect(url)
		           .ignoreContentType(true)
		           .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")  
		           .referrer("http://www.google.com")   
		           .timeout(12000) 
		           .followRedirects(true)
		           .execute();
		Document doc = response.parse();
		for (String cid: getCardIds(doc).subList(1, getCardIds(doc).size())) {
			Card card = CardCatalogue.getCardByBlizzardId(cid);
			if (card != null) {
				cards.add(card);
				if (card.getHeroClass() != HeroClass.ANY) {
					heroClass = card.getHeroClass();
				}
			} else {
				logger.error("Card with id {} could not be found", cid);
			}
		}
		Deck deck = new Deck(heroClass);
		deck.setName(getCardIds(doc).get(0));
		for (Card card : cards) {
			deck.getCards().add(card);
		}
		if (!deck.isComplete()) {
			return null;
		}
		return deck;
	}
}
