package net.demilich.metastone.game.cards;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.PlayCardAction;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.targeting.CardLocation;

public abstract class ChooseOneCard extends Card implements IChooseOneCard {

	private Card card1;
	private Card card2;

	public ChooseOneCard(String name, CardType cardType, Rarity rarity, HeroClass classRestriction, int manaCost) {
		super(name, cardType, rarity, classRestriction, manaCost);
		setTag(GameTag.CHOOSE_ONE);
	}

	@Override
	public Card clone() {
		ChooseOneCard clone = (ChooseOneCard) super.clone();
		clone.card1 = card1.clone();
		clone.card2 = card2.clone();
		return clone;
	}

	@Override
	public PlayCardAction play() {
		throw new UnsupportedOperationException("The method .play() should not be called for ChooseOneCard");
	}

	@Override
	public PlayCardAction playOption1() {
		return card1.play();
	}

	@Override
	public PlayCardAction playOption2() {
		return card2.play();
	}

	public void setCard1(Card card1) {
		this.card1 = card1;
	}

	public void setCard2(Card card2) {
		this.card2 = card2;
	}

	@Override
	public void setId(int id) {
		super.setId(id);
		card1.setId(id);
		card2.setId(id);
	}

	@Override
	public void setLocation(CardLocation cardLocation) {
		super.setLocation(cardLocation);
		card1.setLocation(cardLocation);
		card2.setLocation(cardLocation);
	}

	@Override
	public void setOwner(int ownerId) {
		super.setOwner(ownerId);
		card1.setOwner(ownerId);
		card2.setOwner(ownerId);
	}

}
