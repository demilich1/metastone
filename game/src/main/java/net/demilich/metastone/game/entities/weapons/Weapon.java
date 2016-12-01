package net.demilich.metastone.game.entities.weapons;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.GameContext;
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

	public Weapon(Card sourceCard) {
		super(sourceCard);
	}

	@Override
	public Weapon clone() {
		return (Weapon) super.clone();
	}

	public int getBaseDurability() {
		return getAttributeValue(Attribute.BASE_HP);
	}

	public int getDurability() {
		return getAttributeValue(Attribute.HP);
	}

	@Override
	public EntityType getEntityType() {
		return EntityType.WEAPON;
	}

	public int getMaxDurability() {
		return getAttributeValue(Attribute.MAX_HP) + getAttributeValue(Attribute.HP_BONUS);
	}

	public int getWeaponDamage() {
		return Math.max(0, getAttributeValue(Attribute.ATTACK) + getAttributeValue(Attribute.CONDITIONAL_ATTACK_BONUS)) + getAttributeValue(Attribute.ATTACK_BONUS);
	}

	public boolean isActive() {
		return active;
	}

	public boolean isBroken() {
		return !hasAttribute(Attribute.HP);
	}

	@Override
	public boolean isDestroyed() {
		return hasAttribute(Attribute.DESTROYED) || isBroken();
	}

	public void onEquip(GameContext context, Player player) {
		if (onEquip != null) {
			context.getLogic().castSpell(player.getId(), onEquip, getReference(), EntityReference.NONE, false);
		}
	}

	public void onUnequip(GameContext context, Player player) {
		if (onUnequip != null) {
			context.getLogic().castSpell(player.getId(), onUnequip, getReference(), EntityReference.NONE, false);
		}
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setOnEquip(SpellDesc onEquip) {
		this.onEquip = onEquip;
	}

	public void setOnUnequip(SpellDesc onUnequip) {
		this.onUnequip = onUnequip;
	}

	@Override
	public String toString() {
		String result = "[" + getEntityType() + " '" + getName() + "'id:" + getId() + " ";
		result += getWeaponDamage() + "/" + getDurability();
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
