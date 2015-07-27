package net.demilich.metastone.game.cards;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.PlayCardAction;
import net.demilich.metastone.game.cards.desc.ChooseOneCardDesc;
import net.demilich.metastone.game.entities.heroes.HeroClass;

public class ChooseOneCard extends Card implements IChooseOneCard {

	private String card1Id;
	private String card2Id;

	public ChooseOneCard(ChooseOneCardDesc desc) {
		super(desc);
		setTag(GameTag.CHOOSE_ONE);
		card1Id = desc.option1;
		card2Id = desc.option2;
	}
	
	@Override
	public Card clone() {
		ChooseOneCard clone = (ChooseOneCard) super.clone();
		clone.card1Id = card1Id;
		clone.card2Id = card2Id;
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
	public PlayCardAction playOption1() {
		Card card1 = getCard(card1Id);
		PlayCardAction card1Action = card1.play();
		card1Action.setActionSuffix(card1.getName());
		card1Action.setGroupIndex(0);
		return card1Action;
	}

	@Override
	public PlayCardAction playOption2() {
		Card card2 = getCard(card2Id);
		PlayCardAction card2Action = card2.play();
		card2Action.setActionSuffix(card2.getName());
		card2Action.setGroupIndex(1);
		return card2Action;
	}

}
