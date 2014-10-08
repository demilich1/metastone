package net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder;

import java.util.ArrayList;
import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import de.pferdimanzug.nittygrittymvc.SimpleCommand;
import de.pferdimanzug.nittygrittymvc.interfaces.INotification;

public class FilterCardsByTextCommand extends SimpleCommand<GameNotification> {

	@Override
	public void execute(INotification<GameNotification> notification) {
		String filterText = (String) notification.getBody();
		filterText = filterText.toLowerCase();
		
		DeckProxy deckProxy = (DeckProxy) getFacade().retrieveProxy(DeckProxy.NAME);
		
		List<Card> cards = deckProxy.getCards(deckProxy.getActiveDeck().getHeroClass());
		List<Card> filteredCards = new ArrayList<Card>();
		for (Card card : cards) {
			if (card.matchesFilter(filterText)) {
				filteredCards.add(card);
			}
		}
		
		getFacade().sendNotification(GameNotification.FILTERED_CARDS, filteredCards);
	}


}
