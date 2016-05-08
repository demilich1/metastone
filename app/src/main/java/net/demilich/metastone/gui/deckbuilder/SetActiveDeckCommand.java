package net.demilich.metastone.gui.deckbuilder;

import net.demilich.nittygrittymvc.SimpleCommand;
import net.demilich.nittygrittymvc.interfaces.INotification;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.game.decks.Deck;

public class SetActiveDeckCommand extends SimpleCommand<GameNotification> {

	@Override
	public void execute(INotification<GameNotification> notification) {
		DeckProxy deckProxy = (DeckProxy) getFacade().retrieveProxy(DeckProxy.NAME);
		Deck activeDeck = (Deck) notification.getBody();
		deckProxy.setActiveDeck(activeDeck);

		getFacade().sendNotification(GameNotification.EDIT_DECK, activeDeck);
		getFacade().sendNotification(GameNotification.FILTERED_CARDS, deckProxy.getCards(activeDeck.getHeroClass()));
		getFacade().sendNotification(GameNotification.ACTIVE_DECK_CHANGED, activeDeck);
	}

}
