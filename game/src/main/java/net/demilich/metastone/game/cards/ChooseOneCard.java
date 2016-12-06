package net.demilich.metastone.game.cards;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.actions.PlayCardAction;
import net.demilich.metastone.game.actions.PlayChooseOneCardAction;
import net.demilich.metastone.game.cards.desc.ChooseOneCardDesc;

public class ChooseOneCard extends Card implements IChooseOneCard {

	private String[] cardIds;
	private String cardId;

	public ChooseOneCard(ChooseOneCardDesc desc) {
		super(desc);
		setAttribute(Attribute.CHOOSE_ONE);
		cardIds = desc.options;
		cardId = desc.bothOptions;
	}

	@Override
	public Card clone() {
		ChooseOneCard clone = (ChooseOneCard) super.clone();
		clone.cardIds = cardIds;
		clone.cardId = cardId;
		return clone;
	}

	private Card getCard(String cardId) {
		Card card = CardCatalogue.getCardById(cardId);
		card.setLocation(getLocation());
		card.setOwner(getOwner());
		card.setId(getId());
		return card;
	}
	
	public Card[] getChoiceCards() {
		Card[] cards = new Card[cardIds.length];
		for (int i = 0; i < cardIds.length; i++) {
			cards[i] = getCard(cardIds[i]);
		}
		return cards;
	}
	
	public Card getBothChoicesCard() {
		return getCard(cardId);
	}

	public boolean hasBothOptions() {
		return cardId != null;
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
			SpellCard card = (SpellCard) getCard(cardId);
			PlayChooseOneCardAction cardAction = new PlayChooseOneCardAction(card.getSpell(), this, cardId, card.getTargetRequirement());
			cardAction.setActionSuffix(card.getName());
			cardAction.setGroupIndex(i);
			actions[i] = cardAction;
		}
		return actions;
	}

	@Override
	public PlayCardAction playBothOptions() {
		SpellCard card = (SpellCard) getCard(cardId);
		PlayChooseOneCardAction cardAction = new PlayChooseOneCardAction(card.getSpell(), this, cardId, card.getTargetRequirement());
		cardAction.setActionSuffix(card.getName());
		cardAction.setActionSuffix(card.getName());
		return cardAction;
	}

}
