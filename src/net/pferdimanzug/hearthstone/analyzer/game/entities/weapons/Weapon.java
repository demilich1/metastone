package net.pferdimanzug.hearthstone.analyzer.game.entities.weapons;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.EntityType;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;

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
	
	public Race getRace() {
		return (Race) getTag(GameTag.RACE);
	}
	
	public int getWeaponDamage() {
		return getTagValue(GameTag.WEAPON_DAMAGE);
	}

	public boolean isBroken() {
		return !hasTag(GameTag.DURABILITY) || getTagValue(GameTag.DURABILITY) < 1;
	}

	public void setRace(Race race) {
		setTag(GameTag.RACE, race);
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
