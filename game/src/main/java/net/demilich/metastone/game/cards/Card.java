package net.demilich.metastone.game.cards;

import java.util.EnumMap;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.PlayCardAction;
import net.demilich.metastone.game.cards.desc.CardDesc;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.EntityType;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.desc.BattlecryDesc;
import net.demilich.metastone.game.spells.desc.valueprovider.ValueProvider;
import net.demilich.metastone.game.targeting.CardLocation;
import net.demilich.metastone.game.targeting.CardReference;
import net.demilich.metastone.game.targeting.IdFactory;

public abstract class Card extends Entity {
	private String description = "";
	private CardType cardType;
	private CardSet cardSet;
	private Rarity rarity;
	private HeroClass heroClass;
	private HeroClass[] heroClasses;
	private boolean collectible = true;
	private CardLocation location;
	private ValueProvider manaCostModifier;
	private String cardId;

	public Card() {
		super();
	}

	public Card(CardDesc desc) {
		cardId = desc.id;
		setName(desc.name);
		setDescription(desc.description);
		setCollectible(desc.collectible);
		cardType = desc.type;
		cardSet = desc.set;
		rarity = desc.rarity;
		heroClass = desc.heroClass;
		if (desc.heroClasses != null) {
			heroClasses = desc.heroClasses;
		}

		setAttribute(Attribute.BASE_MANA_COST, desc.baseManaCost);
		if (desc.attributes != null) {
			attributes.putAll(desc.attributes);
		}

		if (desc.manaCostModifier != null) {
			manaCostModifier = desc.manaCostModifier.create();
		}

		if (desc.passiveTrigger != null) {
			attributes.put(Attribute.PASSIVE_TRIGGER, desc.passiveTrigger);
		}

		if (desc.deckTrigger != null) {
			attributes.put(Attribute.DECK_TRIGGER, desc.deckTrigger);
		}
	}

	@Override
	public Card clone() {
		Card clone = (Card) super.clone();
		clone.attributes = new EnumMap<>(getAttributes());
		return clone;
	}

	public int getBaseManaCost() {
		return getAttributeValue(Attribute.BASE_MANA_COST);
	}

	public String getCardId() {
		return cardId;
	}

	public CardReference getCardReference() {
		return new CardReference(getOwner(), getLocation(), getId(), getName());
	}

	public CardSet getCardSet() {
		return cardSet;
	}

	public CardType getCardType() {
		return cardType;
	}

	public HeroClass getHeroClass() {
		return heroClass;
	}

	public HeroClass[] getHeroClasses() {
		return heroClasses;
	}

	public Card getCopy() {
		Card copy = clone();
		copy.setId(IdFactory.UNASSIGNED);
		copy.setLocation(CardLocation.PENDING);
		copy.removeAttribute(Attribute.ATTACK_BONUS);
		copy.removeAttribute(Attribute.HP_BONUS);
		copy.removeAttribute(Attribute.MANA_COST_MODIFIER);
		return copy;
	}

	public String getDescription() {
		// Cleanup the html tags that appear in the description
		// TODO: Show effects on card behaviour like increased spell damage
		if (description == null || description.isEmpty()) {
			return description;
		}
		String descriptionCleaned = description.replaceAll("(</?[bi]>)|\\[x\\]", "");
		return descriptionCleaned;
	}

	@Override
	public EntityType getEntityType() {
		return EntityType.CARD;
	}

	public CardLocation getLocation() {
		return location;
	}

	public int getManaCost(GameContext context, Player player) {
		int actualManaCost = getBaseManaCost();
		if (manaCostModifier != null) {
			actualManaCost -= manaCostModifier.getValue(context, player, null, this);
		}
		return actualManaCost;
	}

	public Rarity getRarity() {
		return rarity;
	}

	public boolean hasHeroClass(HeroClass heroClass) {
		if (getHeroClasses() != null) {
			for (HeroClass h : getHeroClasses()) {
				if (heroClass.equals(h)) {
					return true;
				}
			}
		} else if (heroClass == getHeroClass()) {
			return true;
		}
		return false;
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
		/*
		 * String className = getClass().getName(); if (filter.contains("gvg")
		 * && className.contains("goblins")) { return true; } else if
		 * (filter.contains("naxx") && className.contains("naxx")) { return
		 * true; } else if ((filter.contains("classic") ||
		 * filter.contains("vanilla")) && !className.contains("naxx") &&
		 * !className.contains("goblins")) { return true; }
		 */
		String lowerCaseName = getName().toLowerCase();
		if (lowerCaseName.contains(filter)) {
			return true;
		}

		if (getDescription() != null) {
			String lowerCaseDescription = getDescription().toLowerCase();
			if (lowerCaseDescription.contains(filter)) {
				return true;
			}
		}

		return false;
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
		return String.format("[%s '%s' %s Manacost:%d]", getCardType(), getName(), getReference(), getBaseManaCost());
	}

}
