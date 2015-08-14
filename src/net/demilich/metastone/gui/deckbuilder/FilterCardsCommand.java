package net.demilich.metastone.gui.deckbuilder;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import de.pferdimanzug.nittygrittymvc.SimpleCommand;
import de.pferdimanzug.nittygrittymvc.interfaces.INotification;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardSet;

public class FilterCardsCommand extends SimpleCommand<GameNotification> {

	private static List<Card> filterBySet(List<Card> collection, CardSet set) {
		if (set == CardSet.ANY) {
			return collection;
		}
		collection.removeIf(card -> card.getCardSet() != set);
		return collection;
	}

	private static List<Card> filterByText(List<Card> collection, String text) {
		if (StringUtils.isBlank(text)) {
			return collection;
		}

		String filterText = text.toLowerCase();
		collection.removeIf(card -> !card.matchesFilter(filterText));
		return collection;
	}

	@Override
	public void execute(INotification<GameNotification> notification) {
		CardFilter filter = (CardFilter) notification.getBody();

		DeckProxy deckProxy = (DeckProxy) getFacade().retrieveProxy(DeckProxy.NAME);

		List<Card> cards = deckProxy.getCards(deckProxy.getActiveDeck().getHeroClass());
		cards = filterBySet(cards, filter.getSet());
		cards = filterByText(cards, filter.getText());

		getFacade().sendNotification(GameNotification.FILTERED_CARDS, cards);
	}

}
