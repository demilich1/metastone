package net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.decks.Deck;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.pferdimanzug.nittygrittymvc.SimpleCommand;
import de.pferdimanzug.nittygrittymvc.interfaces.INotification;

public class FillDeckWithRandomCardsCommand extends SimpleCommand<GameNotification> {
	
	private static Logger logger = LoggerFactory.getLogger(FillDeckWithRandomCardsCommand.class);

	@Override
	public void execute(INotification<GameNotification> notification) {
		DeckProxy deckProxy = (DeckProxy) getFacade().retrieveProxy(DeckProxy.NAME);

		Deck activeDeck = deckProxy.getActiveDeck();
		List<Card> cards = deckProxy.getCards(activeDeck.getHeroClass());
		while (!activeDeck.isComplete()) {
			Card randomCard = cards.get(ThreadLocalRandom.current().nextInt(cards.size()));
			if (deckProxy.addCardToDeck(randomCard)) {
				logger.debug("Adding card {} to deck.", randomCard);
			}
		}
		getFacade().sendNotification(GameNotification.ACTIVE_DECK_CHANGED, activeDeck);
	}

}
