package net.demilich.metastone.gui.deckbuilder;

import de.pferdimanzug.nittygrittymvc.SimpleCommand;
import de.pferdimanzug.nittygrittymvc.interfaces.INotification;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.game.cards.Card;

public class RemoveCardFromDeckCommand extends SimpleCommand<GameNotification> {

	@Override
	public void execute(INotification<GameNotification> notification) {
		DeckProxy deckProxy = (DeckProxy) getFacade().retrieveProxy(DeckProxy.NAME);
		Card card = (Card) notification.getBody();
		deckProxy.getActiveDeck().getCards().remove(card);
		
		getFacade().sendNotification(GameNotification.ACTIVE_DECK_CHANGED, deckProxy.getActiveDeck());
	}

}
