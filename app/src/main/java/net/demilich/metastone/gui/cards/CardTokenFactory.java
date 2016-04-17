package net.demilich.metastone.gui.cards;

import java.util.ArrayList;
import java.util.List;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;

public class CardTokenFactory {

	private static final int HAND_CARDS = 10;

	private List<HandCard> cachedHandCards = new ArrayList<HandCard>(HAND_CARDS);

	public CardTokenFactory() {
		for (int i = 0; i < HAND_CARDS; i++) {
			cachedHandCards.add(new HandCard());
		}
	}

	public CardToken createHandCard(GameContext context, Card card, Player player) {
		HandCard handCard = getHandCard();
		handCard.setCard(context, card, player);
		return handCard;
	}

	private HandCard getHandCard() {
		for (HandCard handCard : cachedHandCards) {
			if (handCard.getParent() == null) {
				return handCard;
			}
		}
		return new HandCard();
	}

}
