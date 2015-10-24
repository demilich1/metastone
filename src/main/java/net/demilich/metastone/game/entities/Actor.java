package net.demilich.metastone.game.entities;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.costmodifier.CardCostModifier;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.logic.GameLogic;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;

public abstract class Actor extends Entity {

	private Card sourceCard;
	private SpellTrigger spellTrigger;
	private CardCostModifier cardCostModifier;

	public Actor(Card sourceCard) {
		this.setName(sourceCard != null ? sourceCard.getName() : null);
		this.sourceCard = sourceCard;
	}

	public void addDeathrattle(SpellDesc deathrattleSpell) {
		if (!hasAttribute(Attribute.DEATHRATTLES)) {
			setAttribute(Attribute.DEATHRATTLES, new ArrayList<SpellDesc>());
		}
		getDeathrattles().add(deathrattleSpell);
	}

	public boolean canAttackThisTurn() {
		if (hasAttribute(Attribute.CANNOT_ATTACK)) {
			return false;
		}
		if (hasAttribute(Attribute.FROZEN)) {
			return false;
		}
		if (hasAttribute(Attribute.SUMMONING_SICKNESS) && !hasAttribute(Attribute.CHARGE)) {
			return false;
		}
		return getAttack() > 0 && getAttributeValue(Attribute.NUMBER_OF_ATTACKS) > 0;
	}

	@Override
	public Actor clone() {
		Actor clone = (Actor) super.clone();
		clone.attributes = new EnumMap<>(getAttributes());
		clone.spellTrigger = spellTrigger != null ? spellTrigger.clone() : null;
		if (hasAttribute(Attribute.DEATHRATTLES)) {
			clone.removeAttribute(Attribute.DEATHRATTLES);
			for (SpellDesc deathrattleSpell : getDeathrattles()) {
				SpellDesc deathrattleClone = deathrattleSpell.clone();
				clone.addDeathrattle(deathrattleClone);
			}
		}
		return clone;
	}

	protected boolean displayGameTag(Attribute tag) {
		return tag == Attribute.CHARGE || tag == Attribute.ENRAGED || tag == Attribute.FROZEN || tag == Attribute.DIVINE_SHIELD
				|| tag == Attribute.WINDFURY || tag == Attribute.SPELL_DAMAGE || tag == Attribute.STEALTH || tag == Attribute.TAUNT
				|| tag == Attribute.CANNOT_ATTACK || tag == Attribute.UNTARGETABLE_BY_SPELLS || tag == Attribute.MEGA_WINDFURY;
	}

	public int getAttack() {
		int attack = getAttributeValue(Attribute.ATTACK) + getAttributeValue(Attribute.ATTACK_BONUS)
				+ getAttributeValue(Attribute.AURA_ATTACK_BONUS) + getAttributeValue(Attribute.TEMPORARY_ATTACK_BONUS)
				+ getAttributeValue(Attribute.CONDITIONAL_ATTACK_BONUS);
		return attack;
	}

	public BattlecryAction getBattlecry() {
		return (BattlecryAction) getAttribute(Attribute.BATTLECRY);
	}

	public CardCostModifier getCardCostModifier() {
		return cardCostModifier;
	}

	@SuppressWarnings("unchecked")
	public List<SpellDesc> getDeathrattles() {
		return (List<SpellDesc>) getAttribute(Attribute.DEATHRATTLES);
	}

	public int getHp() {
		return getAttributeValue(Attribute.HP);
	}

	public int getMaxHp() {
		return getAttributeValue(Attribute.MAX_HP) + getAttributeValue(Attribute.HP_BONUS)
				+ getAttributeValue(Attribute.AURA_HP_BONUS);
	}

	public Race getRace() {
		return (Race) getAttribute(Attribute.RACE);
	}

	public Card getSourceCard() {
		return sourceCard;
	}

	public SpellTrigger getSpellTrigger() {
		return spellTrigger;
	}

	public boolean hasSpellTrigger() {
		return spellTrigger != null;
	}
	
	public int getMaxNumberOfAttacks() {
		if (hasAttribute(Attribute.MEGA_WINDFURY)) {
			return GameLogic.MEGA_WINDFURY_ATTACKS;
		} else if (hasAttribute(Attribute.WINDFURY)) {
			return GameLogic.WINDFURY_ATTACKS;
		}
		return 1;
	}


	@Override
	public boolean isDestroyed() {
		return getHp() < 1 || super.isDestroyed();
	}

	public boolean isWounded() {
		return getHp() != getMaxHp();
	}

	public void modifyAuraHpBonus(int value) {
		modifyAttribute(Attribute.AURA_HP_BONUS, value);
		if (value > 0) {
			modifyAttribute(Attribute.HP, value);
		}
		if (getHp() > getMaxHp()) {
			setHp(getMaxHp());
		}
	}

	@Override
	public void modifyHpBonus(int value) {
		modifyAttribute(Attribute.HP_BONUS, value);
		if (value > 0) {
			modifyAttribute(Attribute.HP, value);
		}
		if (getHp() > getMaxHp()) {
			setHp(getMaxHp());
		}
			
	}

	public void setAttack(int value) {
		setAttribute(Attribute.ATTACK, value);
	}

	public void setBaseAttack(int value) {
		setAttribute(Attribute.BASE_ATTACK, value);
		setAttack(value);
	}

	public void setBaseHp(int value) {
		setAttribute(Attribute.BASE_HP, value);
		setMaxHp(value);
		setHp(value);
	}

	public void setBattlecry(BattlecryAction battlecry) {
		setAttribute(Attribute.BATTLECRY, battlecry);
	}

	public void setCardCostModifier(CardCostModifier cardCostModifier) {
		this.cardCostModifier = cardCostModifier;
	}

	public void setHp(int value) {
		setAttribute(Attribute.HP, value);
	}

	public void setMaxHp(int value) {
		setAttribute(Attribute.MAX_HP, value);
	}

	@Override
	public void setOwner(int ownerIndex) {
		super.setOwner(ownerIndex);
		if (hasSpellTrigger()) {
			spellTrigger.setHost(this);
		}
	}

	public void setRace(Race race) {
		setAttribute(Attribute.RACE, race);
	}

	public void setSpellTrigger(SpellTrigger spellTrigger) {
		this.spellTrigger = spellTrigger;
	}

	@Override
	public String toString() {
		String result = "[" + getEntityType() + " '" + getName() + "'id:" + getId() + " ";
		result += getAttack() + "/" + getHp();
		String prefix = " ";
		for (Attribute tag : getAttributes().keySet()) {
			if (displayGameTag(tag)) {
				result += prefix + tag;
				prefix = ", ";
			}
		}
		result += " hashCode: " + hashCode();
		result += "]";
		return result;
	}
}
