package net.demilich.metastone.game.cards;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.pferdimanzug.nittygrittymvc.Proxy;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.game.cards.desc.CardDesc;

public class CardProxy extends Proxy<GameNotification> {

	public final static String NAME = "CardProxy";

	private static Logger logger = LoggerFactory.getLogger(CardProxy.class);

	private final Map<String, CardDesc> cardDesc = new HashMap<String, CardDesc>();

	public CardProxy() {
		super(NAME);
		try {
			loadCards();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void loadCards() throws FileNotFoundException {
		cardDesc.clear();
		File folder = new File("./cards/");
		if (!folder.exists()) {
			logger.error("/cards directory not found");
			return;
		}

		Collection<File> files = FileUtils.listFiles(folder, new String[] { "json" }, true);
		CardParser cardParser = new CardParser();
		for (File file : files) {
			CardDesc desc = cardParser.parseCard(file);
			cardDesc.put(desc.id, desc);
		}

		for (CardDesc desc : cardDesc.values()) {
			Card instance = desc.createInstance();
			CardCatalogue.add(instance);
			logger.info("Adding {} to CardCatalogue", instance);
		}
	}

}
