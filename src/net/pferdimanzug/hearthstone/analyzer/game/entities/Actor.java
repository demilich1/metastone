package net.pferdimanzug.hearthstone.analyzer.game.entities;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.costmodifier.CardCostModifier;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;

public abstract class Actor extends Entity {

	private Card sourceCard;
	private SpellTrigger spellTrigger;
	private CardCostModifier cardCostModifier;

	public Actor(Card sourceCard) {
		this.setName(sourceCard != null ? sourceCard.getName() : null);
		this.sourceCard = sourceCard;
	}
	
	public void addDeathrattle(SpellDesc deathrattleSpell) {
		if (!hasTag(GameTag.DEATHRATTLES)) {
			setTag(GameTag.DEATHRATTLES, new ArrayList<SpellDesc>());
		}
		getDeathrattles().add(deathrattleSpell);
	}

	public boolean canAttackThisTurn() {
		if (hasStatus(GameTag.CANNOT_ATTACK)) {
			return false;
		}
		if (hasStatus(GameTag.SUMMONING_SICKNESS) && !hasStatus(GameTag.CHARGE)) {
			return false;
		}
		return getAttack() > 0 && getTagValue(GameTag.NUMBER_OF_ATTACKS) > 0;
	}

	@Override
	public Actor clone() {
		Actor clone = (Actor) super.clone();
		clone.tags = new EnumMap<>(getTags());
		clone.spellTrigger = spellTrigger != null ? spellTrigger.clone() : null;
		if (hasTag(GameTag.DEATHRATTLES)) {
			clone.removeTag(GameTag.DEATHRATTLES);
			for (SpellDesc deathrattleSpell : getDeathrattles()) {
				SpellDesc deathrattleClone = deathrattleSpell.clone();
				clone.addDeathrattle(deathrattleClone);
			}
		}
		return clone;
	}

	protected boolean displayGameTag(GameTag tag) {
		return tag == GameTag.CHARGE || tag == GameTag.ENRAGED || tag == GameTag.FROZEN || tag == GameTag.DIVINE_SHIELD
				|| tag == GameTag.WINDFURY || tag == GameTag.SPELL_POWER || tag == GameTag.STEALTHED || tag == GameTag.TAUNT
				|| tag == GameTag.CANNOT_ATTACK || tag == GameTag.UNTARGETABLE_BY_SPELLS || tag == GameTag.MEGA_WINDFURY;
	}

	public int getAttack() {
		int attack = getTagValue(GameTag.ATTACK) + getTagValue(GameTag.AURA_ATTACK_BONUS)
				+ getTagValue(GameTag.TEMPORARY_ATTACK_BONUS) + getTagValue(GameTag.CONDITIONAL_ATTACK_BONUS);
		return attack * getAttackMultiplier();
	}

	public int getAttackMultiplier() {
		return hasTag(GameTag.ATTACK_MULTIPLIER) ? getTagValue(GameTag.ATTACK_MULTIPLIER) : 1;
	}

	public Battlecry getBattlecry() {
		return (Battlecry) getTag(GameTag.BATTLECRY);
	}

	public CardCostModifier getCardCostModifier() {
		return cardCostModifier;
	}

	@SuppressWarnings("unchecked")
	public List<SpellDesc> getDeathrattles() {
		return (List<SpellDesc>) getTag(GameTag.DEATHRATTLES);
	}

	public int getHp() {
		return getTagValue(GameTag.HP);
	}

	public int getMaxHp() {
		return getTagValue(GameTag.MAX_HP) + getTagValue(GameTag.HP_BONUS);
	}

	public Race getRace() {
		return (Race) getTag(GameTag.RACE);
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
		return getHp() < 1 || super.isDead();
	}

	public boolean isWounded() {
		return getHp() != getMaxHp();
	}

	public void modifyAuraHpBonus(int value) {
		modifyTag(GameTag.AURA_HP_BONUS, value);
		modifyTag(GameTag.HP, value);
	}

	public void modifyHpBonus(int value) {
		modifyTag(GameTag.HP_BONUS, value);
		modifyTag(GameTag.HP, value);
	}

	public void setAttack(int value) {
		setTag(GameTag.ATTACK, value);
	}
	
	public void setBaseAttack(int value) {
		setTag(GameTag.BASE_ATTACK, value);
		setAttack(value);
	}

	public void setBaseHp(int value) {
		setTag(GameTag.BASE_HP, value);
		setMaxHp(value);
		setHp(value);
	}

	public void setBattlecry(Battlecry battlecry) {
		setTag(GameTag.BATTLECRY, battlecry);
	}

	public void setCardCostModifier(CardCostModifier cardCostModifier) {
		this.cardCostModifier = cardCostModifier;
	}

	public void setHp(int value) {
		setTag(GameTag.HP, value);
	}

	public void setMaxHp(int value) {
		setTag(GameTag.MAX_HP, value);
	}

	@Override
	public void setOwner(int ownerIndex) {
		super.setOwner(ownerIndex);
		if (hasSpellTrigger()) {
			spellTrigger.setHost(this);
		}
	}

	public void setRace(Race race) {
		setTag(GameTag.RACE, race);
	}

	public void setSpellTrigger(SpellTrigger spellTrigger) {
		this.spellTrigger = spellTrigger;
	}

	@Override
	public String toString() {
		String result = "[" + getEntityType() + " '" + getName() + "'id:" + getId() + " ";
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
