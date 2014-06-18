package net.pferdimanzug.hearthstone.analyzer.game.cards;

import java.util.HashMap;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.PlayCardAction;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.EntityType;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;

public abstract class Card extends Entity {

	private String description = "";
	private final CardType cardType;
	private final int manaCost;
	private final Rarity rarity;
	private final HeroClass classRestriction;
	private boolean collectible = true;
	
	public Card(String name, CardType cardType, Rarity rarity, HeroClass classRestriction, int manaCost) {
		setName(name);
		this.cardType = cardType;
		this.rarity = rarity;
		this.classRestriction = classRestriction;
		this.manaCost = manaCost;
	}
	
	@Override
	public Card clone() {
		try {
			Card clone = (Card) super.clone();
			clone.tags = new HashMap<>(getTags());
			return clone;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int getBaseManaCost() {
		return manaCost;
	}

	public CardType getCardType() {
		return cardType;
	}

	public HeroClass getClassRestriction() {
		return classRestriction;
	}

	public String getDescription() {
		return description;
	}
	
	@Override
	public EntityType getEntityType() {
		return EntityType.CARD;
	}

	public int getManaCost(Player player) {
		return manaCost + getTagValue(GameTag.MANA_COST_MODIFIER);
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

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return String.format("[%s '%s' Manacost:%d]", getCardType(), getName(), manaCost);
	}

}
