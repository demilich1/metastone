package net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder;

import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.decks.Deck;
import de.pferdimanzug.nittygrittymvc.SimpleCommand;
import de.pferdimanzug.nittygrittymvc.interfaces.INotification;

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
