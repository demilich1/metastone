package net.demilich.metastone.game.cards;

import java.util.EnumMap;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.PlayCardAction;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.EntityType;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.targeting.CardLocation;
import net.demilich.metastone.game.targeting.CardReference;
import net.demilich.metastone.game.targeting.IdFactory;

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
		if (getRarity().toString().toLowerCase().contains(filter)) {
			return true;
		}
		String className = getClass().getName();
		if (filter.contains("gvg") && className.contains("goblins")) {
			return true;
		} else if (filter.contains("naxx") && className.contains("naxx")) {
			return true;
		} else if ((filter.contains("classic") || filter.contains("vanilla")) && !className.contains("naxx")
				&& !className.contains("goblins")) {
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
