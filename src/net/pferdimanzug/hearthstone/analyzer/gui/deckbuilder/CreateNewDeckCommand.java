package net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder;

import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.decks.Deck;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import de.pferdimanzug.nittygrittymvc.SimpleCommand;
import de.pferdimanzug.nittygrittymvc.interfaces.INotification;

public class CreateNewDeckCommand extends SimpleCommand<GameNotification> {

	@Override
	public void execute(INotification<GameNotification> notification) {
		DeckProxy deckProxy = (DeckProxy) getFacade().retrieveProxy(DeckProxy.NAME);
		HeroClass heroClass = (HeroClass) notification.getBody();

		Deck newDeck = new Deck(heroClass);
		//TODO: change
		int randomNumber = (int)(Math.random() * 100);
		newDeck.setName("Deck " + randomNumber);
		deckProxy.setActiveDeck(newDeck);

		getFacade().sendNotification(GameNotification.EDIT_DECK, newDeck);
		getFacade().sendNotification(GameNotification.FILTERED_CARDS, deckProxy.getCards(heroClass));
		getFacade().sendNotification(GameNotification.ACTIVE_DECK_CHANGED, newDeck);
	}

}
