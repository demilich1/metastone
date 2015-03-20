package net.demilich.metastone.game.entities.weapons;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.EntityType;

public class Weapon extends Actor {

	private boolean active;

	public Weapon(Card sourceCard, int weaponDamage, int durability) {
		super(sourceCard);
		setTag(GameTag.WEAPON_DAMAGE, weaponDamage);
		setTag(GameTag.DURABILITY, durability);
	}

	@Override
	public Weapon clone() {
		return (Weapon) super.clone();
	}

	public int getDurability() {
		return getTagValue(GameTag.DURABILITY);
	}

	@Override
	public EntityType getEntityType() {
		return EntityType.WEAPON;
	}
	
	public int getWeaponDamage() {
		return getTagValue(GameTag.WEAPON_DAMAGE) + getTagValue(GameTag.CONDITIONAL_ATTACK_BONUS);
	}

	public boolean isActive() {
		return active;
	}

	public boolean isBroken() {
		return !hasStatus(GameTag.DURABILITY) || getTagValue(GameTag.WEAPON_DAMAGE) <= 0;
	}

	@Override
	public boolean isDead() {
		return hasStatus(GameTag.DEAD) || isBroken();
	}

	public void onEquip(GameContext context, Player player) {
		
	}
	
	public void onUnequip(GameContext context, Player player) {
		
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
}
