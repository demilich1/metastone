package net.demilich.metastone.game.cards;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.actions.PlayCardAction;
import net.demilich.metastone.game.cards.desc.ChooseOneCardDesc;

public class ChooseOneCard extends Card implements IChooseOneCard {

	private String[] cardIds;

	public ChooseOneCard(ChooseOneCardDesc desc) {
		super(desc);
		setAttribute(Attribute.CHOOSE_ONE);
		cardIds = desc.options;
	}

	@Override
	public Card clone() {
		ChooseOneCard clone = (ChooseOneCard) super.clone();
		clone.cardIds = cardIds;
		return clone;
	}

	private Card getCard(String cardId) {
		Card card = CardCatalogue.getCardById(cardId);
		card.setLocation(getLocation());
		card.setOwner(getOwner());
		card.setId(getId());
		return card;
	}

	@Override
	public PlayCardAction play() {
		throw new UnsupportedOperationException("The method .play() should not be called for ChooseOneCard");
	}

	@Override
	public PlayCardAction[] playOptions() {
		PlayCardAction[] actions = new PlayCardAction[cardIds.length];
		for (int i = 0; i < cardIds.length; i++) {
			String cardId = cardIds[i];
			Card card = getCard(cardId);
			PlayCardAction cardAction = card.play();
			cardAction.setActionSuffix(card.getName());
			cardAction.setGroupIndex(i);
			actions[i] = cardAction;
		}
		return actions;
	}

}
