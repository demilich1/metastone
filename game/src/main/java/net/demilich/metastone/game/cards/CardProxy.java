package net.demilich.metastone.game.cards;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.demilich.metastone.utils.ResourceInputStream;
import net.demilich.metastone.utils.ResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.demilich.metastone.GameNotification;
import net.demilich.metastone.game.cards.desc.CardDesc;
import net.demilich.nittygrittymvc.Proxy;

public class CardProxy extends Proxy<GameNotification> {

	public final static String NAME = "CardProxy";
	private static final String CARDS_FOLDER = File.separator + "cards";

	private static Logger logger = LoggerFactory.getLogger(CardProxy.class);

	public CardProxy() {
		super(NAME);
		try {
			loadCards();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadCards() throws IOException, URISyntaxException {

		// load cards from cards.jar on the classpath
		Collection<ResourceInputStream> inputStreams = ResourceLoader.loadJsonInputStreams(CARDS_FOLDER, false);

		// TODO: read cards from ~/metastone/cards

		Map<String, CardDesc> cardDesc = new HashMap<String, CardDesc>();
		CardParser cardParser = new CardParser();
		for (ResourceInputStream resourceInputStream : inputStreams) {
			try {
				CardDesc desc = cardParser.parseCard(resourceInputStream);
				if (cardDesc.containsKey(desc.id)) {
					logger.error("Card id {} is duplicated!", desc.id);
				}
				cardDesc.put(desc.id, desc);
			} catch (Exception e) {
				logger.error("Trouble reading " + resourceInputStream.fileName);
				throw e;
			}
		}

		for (CardDesc desc : cardDesc.values()) {
			Card instance = desc.createInstance();
			CardCatalogue.add(instance);
			logger.debug("Adding {} to CardCatalogue", instance);
		}
	}

}
