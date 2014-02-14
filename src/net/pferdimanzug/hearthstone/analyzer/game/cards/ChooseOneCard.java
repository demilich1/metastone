package net.pferdimanzug.hearthstone.analyzer.game.cards;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.PlayCardAction;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;

public abstract class ChooseOneCard extends Card {

	private Card card1;
	private Card card2;

	public ChooseOneCard(String name, CardType cardType, Rarity rarity, HeroClass classRestriction, int manaCost) {
		super(name, cardType, rarity, classRestriction, manaCost);
		setTag(GameTag.CHOOSE_ONE);
	}

	public Card getCard1() {
		return card1;
	}

	public void setCard1(Card card1) {
		this.card1 = card1;
	}

	public Card getCard2() {
		return card2;
	}

	public void setCard2(Card card2) {
		this.card2 = card2;
	}

	public PlayCardAction playCard1() {
		return card1.play();
	}

	public PlayCardAction playCard2() {
		return card2.play();
	}

	@Override
	public void setId(int id) {
		super.setId(id);
		card1.setId(id);
		card2.setId(id);
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
		// this method should not be called for this type of card
		return null;
	}

}
