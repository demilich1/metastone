package net.demilich.metastone.game.entities.weapons;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.EntityType;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class Weapon extends Actor {

	private boolean active;
	private SpellDesc onEquip;
	private SpellDesc onUnequip;

	public Weapon(Card sourceCard, int weaponDamage, int durability) {
		super(sourceCard);
		setTag(GameTag.ATTACK, weaponDamage);
		setTag(GameTag.HP, durability);
	}

	@Override
	public Weapon clone() {
		return (Weapon) super.clone();
	}

	public int getDurability() {
		return getTagValue(GameTag.HP);
	}

	@Override
	public EntityType getEntityType() {
		return EntityType.WEAPON;
	}
	
	public int getWeaponDamage() {
		return getTagValue(GameTag.ATTACK) + getTagValue(GameTag.CONDITIONAL_ATTACK_BONUS);
	}

	public boolean isActive() {
		return active;
	}

	public boolean isBroken() {
		return !hasStatus(GameTag.HP) || getTagValue(GameTag.ATTACK) <= 0;
	}

	@Override
	public boolean isDead() {
		return hasStatus(GameTag.DEAD) || isBroken();
	}

	public void onEquip(GameContext context, Player player) {
		if (onEquip != null) {
			context.getLogic().castSpell(player.getId(), onEquip, getReference(), EntityReference.NONE);
		}
	}
	
	public void onUnequip(GameContext context, Player player) {
		if (onUnequip != null) {
			context.getLogic().castSpell(player.getId(), onUnequip, getReference(), EntityReference.NONE);
		}
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		String result = "[" + getEntityType() + " '" + getName() + "'id:" + getId() + " ";
		result += getWeaponDamage() + "/" + getDurability();
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

	public void setOnUnequip(SpellDesc onUnequip) {
		this.onUnequip = onUnequip;
	}

	public void setOnEquip(SpellDesc onEquip) {
		this.onEquip = onEquip;
	}
}
