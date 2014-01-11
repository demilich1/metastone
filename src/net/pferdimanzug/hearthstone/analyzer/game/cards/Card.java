package net.pferdimanzug.hearthstone.analyzer.game.cards;

import net.pferdimanzug.hearthstone.analyzer.game.actions.PlayCardAction;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;

public abstract class Card implements Cloneable {

	private final int id;
	private final String name;
	private final CardType cardType;
	private final int manaCost;
	private final Rarity rarity;
	private final HeroClass classRestriction;
	private boolean collectible = true;
	
	public Card(String name, CardType cardType, Rarity rarity, HeroClass classRestriction, int manaCost) {
		this.id = getClass().getName().hashCode();
		this.name = name;
		this.cardType = cardType;
		this.rarity = rarity;
		this.classRestriction = classRestriction;
		this.manaCost = manaCost;
	}
	
	@Override
	public Card clone() {
		try {
			return (Card) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public CardType getCardType() {
		return cardType;
	}

	public HeroClass getClassRestriction() {
		return classRestriction;
	}

	public int getId() {
		return id;
	}

	public int getManaCost() {
		return manaCost;
	}

	public String getName() {
		return name;
	}

	public Rarity getRarity() {
		return rarity;
	}

	public boolean isCollectible() {
		return collectible;
	}
	
	public abstract PlayCardAction play();

	public void setCollectible(boolean collectible) {
		this.collectible = collectible;
	}
	
	@Override
	public String toString() {
		return String.format("[%s '%s' Manacost:%d]", getCardType(), getName(), getManaCost());
	}

}
