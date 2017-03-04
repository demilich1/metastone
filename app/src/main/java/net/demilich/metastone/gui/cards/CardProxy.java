package net.demilich.metastone.gui.cards;

import net.demilich.metastone.GameNotification;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.CardParseException;
import net.demilich.nittygrittymvc.Proxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CardProxy extends Proxy<GameNotification> {

	public final static String NAME = "CardProxy";
	private static Logger logger = LoggerFactory.getLogger(CardProxy.class);

	public CardProxy() {
		super(NAME);
		try {
			// ensure user's personal cards dir exists
			Files.createDirectories(Paths.get(CardCatalogue.CARDS_FOLDER_PATH));
			// ensure cards have been copied to ~/metastone/cards
			CardCatalogue.copyCardsFromResources();
			CardCatalogue.loadCardsFromFilesystem();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("Trouble creating " +  Paths.get(CardCatalogue.CARDS_FOLDER_PATH));
			e.printStackTrace();
		} catch (CardParseException cpe) {
			getFacade().sendNotification(GameNotification.CARD_PARSE_ERROR, cpe.getMessage());
		}
	}
}
