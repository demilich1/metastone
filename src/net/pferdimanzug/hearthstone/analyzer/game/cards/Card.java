package net.pferdimanzug.hearthstone.analyzer.game.cards;

import net.pferdimanzug.hearthstone.analyzer.game.actions.PlayCardAction;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;

public abstract class Card implements Cloneable {

	private final String name;
	private final CardType cardType;
	private final int manaCost;
	private final Rarity rarity;
	private final HeroClass classRestriction;
	private boolean collectible = true;
	
	public Card(String name, CardType cardType, Rarity rarity, HeroClass classRestriction, int manaCost) {
		this.name = name;
		this.cardType = cardType;
		this.rarity = rarity;
		this.classRestriction = classRestriction;
		this.manaCost = manaCost;
	}
	
	public abstract PlayCardAction play();
	
	public int getManaCost() {
		return manaCost;
	}

	public String getName() {
		return name;
	}

	public CardType getCardType() {
		return cardType;
	}

	public Rarity getRarity() {
		return rarity;
	}

	public HeroClass getClassRestriction() {
		return classRestriction;
	}

	public boolean isCollectible() {
		return collectible;
	}

	public void setCollectible(boolean collectible) {
		this.collectible = collectible;
	}
	
	@Override
	public Card clone() {
		try {
			return (Card) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
