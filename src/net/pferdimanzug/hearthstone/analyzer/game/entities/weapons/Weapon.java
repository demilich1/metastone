package net.pferdimanzug.hearthstone.analyzer.game.entities.weapons;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.EntityType;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;

public class Weapon extends Actor {

	public Weapon(Card sourceCard, int weaponDamage, int durability) {
		super(sourceCard);
		setTag(GameTag.WEAPON_DAMAGE, weaponDamage);
		setTag(GameTag.DURABILITY, durability);
	}

	@Override
	public Weapon clone() {
		return (Weapon) super.clone();
	}
	
	@Override
	public EntityType getEntityType() {
		return EntityType.WEAPON;
	}
	
	public int getWeaponDamage() {
		return getTagValue(GameTag.WEAPON_DAMAGE);
	}
	
	public boolean isBroken() {
		return !hasTag(GameTag.DURABILITY) || getTagValue(GameTag.DURABILITY) < 1;
	}
	
	public int getDurability() {
		return getTagValue(GameTag.DURABILITY);
	}

	public Race getRace() {
		return (Race) getTag(GameTag.RACE);
	}

	public void setRace(Race race) {
		setTag(GameTag.RACE, race);
	}

}
