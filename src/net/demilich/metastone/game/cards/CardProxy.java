package net.demilich.metastone.game.cards;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;

import net.demilich.metastone.GameNotification;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.pferdimanzug.nittygrittymvc.Proxy;

public class CardProxy extends Proxy<GameNotification> {
	
	public final static String NAME = "CardProxy";
	
	private static Logger logger = LoggerFactory.getLogger(CardProxy.class);

	public CardProxy() {
		super(NAME);
		try {
			loadCards();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadCards() throws FileNotFoundException {
		//decks.clear();
		File folder = new File("./cards/");
		if (!folder.exists()) {
			logger.error("/cards directory not found");
			return;
		}
		
		Collection<File> files = FileUtils.listFiles(folder, new String[] { "json" }, true);
		CardParser cardParser = new CardParser();
		for (File file : files) {
			cardParser.parseCard(file);
			
		}
	}

}
