package net.pferdimanzug.hearthstone.analyzer.game.entities;

import java.util.HashMap;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;

public abstract class Actor extends Entity {
	
	// index of the owning player, i.e.
	// 0 -> owned by player 1
	// 1 -> owned by player 2
	// implemented it that way because of the same reason
	// the class TargetKey was created - lazy references are better for cloning
	private int ownerIndex;
	private Card sourceCard;
	private SpellTrigger spellTrigger;
	
	public Actor(Card sourceCard) {
		this.setName(sourceCard != null ? sourceCard.getName() : null);
		this.sourceCard = sourceCard;
	}

	public void setSpellTrigger(SpellTrigger spellTrigger) {
		spellTrigger.setHost(this);
		this.spellTrigger = spellTrigger;
	}
	
	public boolean canAttackThisTurn() {
		if (hasTag(GameTag.CANNOT_ATTACK)) {
			return false;
		}
		if (hasTag(GameTag.SUMMONING_SICKNESS) && !hasTag(GameTag.CHARGE)) {
			return false;
		}
		return getAttack() > 0 && getTagValue(GameTag.NUMBER_OF_ATTACKS) > 0;
	}

	@Override
	public Actor clone() {
		try {
			Actor clone = (Actor) super.clone();
			clone.tags = new HashMap<>(getTags());
			clone.spellTrigger = spellTrigger != null ? spellTrigger.clone() : null;
			return clone;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	private boolean displayGameTag(GameTag tag) {
		return tag == GameTag.CHARGE || tag == GameTag.ENRAGED || tag == GameTag.FROZEN || tag == GameTag.DIVINE_SHIELD
				|| tag == GameTag.WINDFURY || tag == GameTag.SPELL_POWER || tag == GameTag.STEALTHED || tag == GameTag.TAUNT || tag == GameTag.CANNOT_ATTACK;
	}

	public int getAttack() {
		return getTagValue(GameTag.BASE_ATTACK) + getTagValue(GameTag.ATTACK_BONUS) + getTagValue(GameTag.AURA_ATTACK_BONUS);
	}

	public Battlecry getBattlecry() {
		return (Battlecry) getTag(GameTag.BATTLECRY);
	}

	public Spell getDeathrattle() {
		return (Spell) getTag(GameTag.DEATHRATTLE);
	}

	public Spell getEnrageSpell() {
		return (Spell) getTag(GameTag.ENRAGE_SPELL);
	}

	public abstract EntityType getEntityType();

	public int getHp() {
		return getTagValue(GameTag.HP);
	}

	public int getMaxHp() {
		return getTagValue(GameTag.MAX_HP) + getTagValue(GameTag.HP_BONUS);
	}

	public int getOwner() {
		return ownerIndex;
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

	public boolean isDead() {
		return getHp() < 1;
	}

	public boolean isWounded() {
		return getHp() != getMaxHp();
	}

	public void modifyHpBonus(int value) {
		modifyTag(GameTag.HP_BONUS, value);
		modifyTag(GameTag.HP, value);
	}
	
	public void modifyAuraHpBonus(int value) {
		modifyTag(GameTag.AURA_HP_BONUS, value);
		modifyTag(GameTag.HP, value);
	}

	public void modifyTag(GameTag tag, int value) {
		if (!hasTag(tag)) {
			setTag(tag, 0);
		}
		setTag(tag, getTagValue(tag) + value);
	}

	public void setBaseAttack(int value) {
		setTag(GameTag.BASE_ATTACK, value);
	}

	public void setBaseHp(int value) {
		setMaxHp(value);
		setHp(value);
	}

	public void setHp(int value) {
		setTag(GameTag.HP, value);
	}

	public void setMaxHp(int value) {
		setTag(GameTag.MAX_HP, value);
	}

	public void setOwner(int ownerIndex) {
		this.ownerIndex = ownerIndex;
		if (hasSpellTrigger()) {
			spellTrigger.setHost(this);
		}
	}

	@Override
	public String toString() {
		String result = "["  + getEntityType() + " '" + getName() + "'id:" + getId() + " ";
		result += getAttack() + "/" + getHp();
		String prefix = " ";
		for (GameTag tag : getTags().keySet()) {
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
