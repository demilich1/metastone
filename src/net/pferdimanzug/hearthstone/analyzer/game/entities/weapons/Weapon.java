package net.pferdimanzug.hearthstone.analyzer.game.entities.weapons;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.EntityType;

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
		return getTagValue(GameTag.WEAPON_DAMAGE);
	}

	public boolean isActive() {
		return active;
	}

	public boolean isBroken() {
		return !hasTag(GameTag.DURABILITY) || getTagValue(GameTag.DURABILITY) < 1;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	@Override
	public String toString() {
		String result = "["  + getEntityType() + " '" + getName() + "'id:" + getId() + " ";
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
