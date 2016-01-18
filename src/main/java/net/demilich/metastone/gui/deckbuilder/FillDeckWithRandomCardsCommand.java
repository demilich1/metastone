package net.demilich.metastone.gui.deckbuilder;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.demilich.nittygrittymvc.SimpleCommand;
import net.demilich.nittygrittymvc.interfaces.INotification;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.decks.Deck;

public class FillDeckWithRandomCardsCommand extends SimpleCommand<GameNotification> {

	private static Logger logger = LoggerFactory.getLogger(FillDeckWithRandomCardsCommand.class);

	@Override
	public void execute(INotification<GameNotification> notification) {
		DeckProxy deckProxy = (DeckProxy) getFacade().retrieveProxy(DeckProxy.NAME);

		Deck activeDeck = deckProxy.getActiveDeck();
		List<Card> cards = deckProxy.getCards(activeDeck.getHeroClass());
		if (activeDeck.isTooBig()) {
			while (!activeDeck.isComplete()) {
				Card randomCard = activeDeck.getCards().getRandom();
				deckProxy.removeCardFromDeck(randomCard);
				logger.debug("Removing card {} to deck.", randomCard);
			}
		} else {
			while (!activeDeck.isComplete()) {
				Card randomCard = cards.get(ThreadLocalRandom.current().nextInt(cards.size()));
				if (deckProxy.addCardToDeck(randomCard)) {
					logger.debug("Adding card {} to deck.", randomCard);
				}
			}
		}
		getFacade().sendNotification(GameNotification.ACTIVE_DECK_CHANGED, activeDeck);
	}

}
