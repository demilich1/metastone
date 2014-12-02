package net.pferdimanzug.hearthstone.analyzer.game.cards;

import java.util.EnumMap;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.PlayCardAction;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.EntityType;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.CardLocation;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.CardReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.IdFactory;

public abstract class Card extends Entity {

	private String description = "";
	private final CardType cardType;
	private final int manaCost;
	private final Rarity rarity;
	private final HeroClass classRestriction;
	private boolean collectible = true;
	private CardLocation location;

	public Card(String name, CardType cardType, Rarity rarity, HeroClass classRestriction, int manaCost) {
		setName(name);
		this.cardType = cardType;
		this.rarity = rarity;
		this.classRestriction = classRestriction;
		this.manaCost = manaCost;
	}

	@Override
	public Card clone() {
		Card clone = (Card) super.clone();
		clone.tags = new EnumMap<>(getTags());
		return clone;
	}

	public int getBaseManaCost() {
		return manaCost;
	}

	public CardReference getCardReference() {
		return new CardReference(getOwner(), getLocation(), getId(), getName());
	}

	public CardType getCardType() {
		return cardType;
	}

	public HeroClass getClassRestriction() {
		return classRestriction;
	}

	public Card getCopy() {
		Card copy = clone();
		copy.setId(IdFactory.UNASSIGNED);
		return copy;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public EntityType getEntityType() {
		return EntityType.CARD;
	}

	public CardLocation getLocation() {
		return location;
	}

	public int getManaCost(GameContext context, Player player) {
		return manaCost + getTagValue(GameTag.MANA_COST_MODIFIER);
	}

	public Rarity getRarity() {
		return rarity;
	}

	public boolean isCollectible() {
		return collectible;
	}

	public boolean matchesFilter(String filter) {
		if (filter == null) {
			return true;
		}
		String lowerCaseName = getName().toLowerCase();
		return lowerCaseName.contains(filter);
	}

	public abstract PlayCardAction play();

	public void setCollectible(boolean collectible) {
		this.collectible = collectible;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setLocation(CardLocation location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return String.format("[%s '%s' Manacost:%d]", getCardType(), getName(), manaCost);
	}

}
