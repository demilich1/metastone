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
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.desc.BattlecryDesc;
import net.demilich.metastone.game.spells.desc.valueprovider.ValueProvider;
import net.demilich.metastone.game.targeting.CardLocation;
import net.demilich.metastone.game.targeting.CardReference;
import net.demilich.metastone.game.targeting.IdFactory;

public abstract class Card extends Entity {

	private String description = "";
	private final CardType cardType;
	private final CardSet cardSet;
	private final Rarity rarity;
	private HeroClass heroClass;
	private HeroClass[] heroClasses;
	private boolean collectible = true;
	private CardLocation location;
	private BattlecryDesc battlecry;
	private ValueProvider manaCostModifier;
	private final String cardId;

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

	public boolean evaluateExpression(String operator, int value1, int value2) {
		switch(operator) {
		case "=":
			return value1 == value2;
		case ">":
			return value1 > value2;
		case "<":
			return value1 < value2;
		case ">=":
			return value1 >= value2;
		case "<=":
			return value1 <= value2;
		case "!=":
			return value1 != value2;
		}
		return false;
	}

	public int getBaseManaCost() {
		return getAttributeValue(Attribute.BASE_MANA_COST);
	}

	public BattlecryDesc getBattlecry() {
		return battlecry;
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
		int actualManaCost = getBaseManaCost();
		if (manaCostModifier != null) {
			actualManaCost -= manaCostModifier.getValue(context, player, null, this);
		}
		return actualManaCost;
	}

	public Rarity getRarity() {
		return rarity;
	}

	public Race getRace() {
		return hasAttribute(Attribute.RACE) ? (Race) getAttribute(Attribute.RACE) : Race.NONE;
	}

	public boolean hasBattlecry() {
		return this.battlecry != null;
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
		if (filter == null || filter == "") {
			return true;
		}
		String[] filters = filter.split(" ");
		for (String splitString : filters) {
			if (!matchesSplitFilter(splitString)) {
				return false;
			}
		}
		return true;
	}

	public boolean matchesSplitFilter(String filter) {
		filter = filter.toLowerCase();
		String[] split = filter.split("((<|>)=?)|(!?=)");
		if (split.length >= 2) {
			int value;
			try {
				value = Integer.parseInt(split[1]);
			} catch (Exception e) {
				return false;
			}
			String operator = filter.substring(split[0].length(), filter.indexOf(split[1], split[0].length() + 1));
			if ((split[0].contains("mana") || split[0].contains("cost")) &&
					evaluateExpression(operator, getBaseManaCost(), value)) {
				return true;
			}
			if (split[0].contains("attack") && hasAttribute(Attribute.BASE_ATTACK) &&
					evaluateExpression(operator, getAttributeValue(Attribute.BASE_ATTACK), value)) {
				return true;
			}
			if ((split[0].contains("health") || split[0].contains("hp")) && hasAttribute(Attribute.BASE_HP) &&
					evaluateExpression(operator, getAttributeValue(Attribute.BASE_HP), value)) {
				return true;
			}
		}
		if (getRarity().toString().toLowerCase().contains(filter)) {
			return true;
		}
		if (getRace() != Race.NONE && getRace().toString().toLowerCase().contains(filter)) {
			return true;
		}
		String cardType = getCardType() == CardType.CHOOSE_ONE ? "SPELL" : getCardType().toString();
		if (cardType.toLowerCase().contains(filter)) {
			return true;
		}
		if ((getHeroClass() == HeroClass.ANY && "neutral".contains(filter))
				|| (getHeroClass() != HeroClass.ANY && (getHeroClass().toString().toLowerCase().contains(filter)
				|| "class".contains(filter)))) {
			return true;
		}
		String lowerCaseName = getName().toLowerCase();
		if (lowerCaseName.contains(filter)) {
			return true;
		}
		String regexName = lowerCaseName.replaceAll("[:,\'\\- ]+", "");
		if (regexName.contains(filter)) {
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

	public void setBattlecry(BattlecryDesc battlecry) {
		this.battlecry = battlecry;
	}

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
