package net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder.metadeck;

import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.decks.Deck;
import net.pferdimanzug.hearthstone.analyzer.game.decks.MetaDeck;
import net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder.DeckProxy;
import de.pferdimanzug.nittygrittymvc.SimpleCommand;
import de.pferdimanzug.nittygrittymvc.interfaces.INotification;

public class AddDeckToMetaDeckCommand extends SimpleCommand<GameNotification> {

	@Override
	public void execute(INotification<GameNotification> notification) {
		DeckProxy deckProxy = (DeckProxy) getFacade().retrieveProxy(DeckProxy.NAME);
		MetaDeck metaDeck = (MetaDeck) deckProxy.getActiveDeck();
		
		Deck deck = (Deck) notification.getBody();
		if (metaDeck.getDecks().contains(deck)) {
			return;
		}
		
		metaDeck.getDecks().add(deck);
		getFacade().sendNotification(GameNotification.ACTIVE_DECK_CHANGED, deckProxy.getActiveDeck());
	}

}
