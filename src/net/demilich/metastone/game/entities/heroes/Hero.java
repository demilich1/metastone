package net.demilich.metastone.game.entities.heroes;

import java.util.EnumMap;
import java.util.Map;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.EntityType;
import net.demilich.metastone.game.entities.weapons.Weapon;
import net.demilich.metastone.game.heroes.powers.HeroPower;

public class Hero extends Actor {

	private HeroClass heroClass;
	private HeroPower heroPower;
	private Weapon weapon;

	public Hero(String name, HeroClass heroClass, HeroPower heroPower) {
		super(null);
		setName(name);
		this.setHeroClass(heroClass);
		this.setHeroPower(heroPower);
	}

	public void activateWeapon(boolean active) {
		if (weapon != null) {
			weapon.setActive(active);
		}
	}

	@Override
	public Hero clone() {
		Hero clone = (Hero) super.clone();
		if (weapon != null) {
			clone.setWeapon(getWeapon().clone());
		}
		clone.setHeroPower((HeroPower) getHeroPower().clone());
		return clone;
	}

	public int getArmor() {
		return getTagValue(GameTag.ARMOR);
	}

	@Override
	public int getAttack() {
		int attack = super.getAttack();
		if (weapon != null && weapon.isActive()) {
			attack += weapon.getWeaponDamage();
		}
		return attack;
	}

	public int getEffectiveHp() {
		return getHp() + getArmor();
	}

	@Override
	public EntityType getEntityType() {
		return EntityType.HERO;
	}

	public HeroClass getHeroClass() {
		return heroClass;
	}

	public HeroPower getHeroPower() {
		return heroPower;
	}

	public Map<GameTag, Object> getTagsCopy() {
		Map<GameTag, Object> copy = new EnumMap<>(GameTag.class);
		for (GameTag tag : tags.keySet()) {
			if (tag != GameTag.COMBO) {
				continue;
			}
			copy.put(tag, tags.get(tag));
		}
		return copy;
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public void modifyArmor(int armor) {
		// armor cannot fall below zero
		int newArmor = Math.max(getArmor() + armor, 0);
		setTag(GameTag.ARMOR, newArmor);
	}

	public void setHeroClass(HeroClass heroClass) {
		this.heroClass = heroClass;
	}

	public void setHeroPower(HeroPower heroPower) {
		this.heroPower = heroPower;
		heroPower.setOwner(getOwner());
	}

	@Override
	public void setOwner(int ownerIndex) {
		super.setOwner(ownerIndex);
		heroPower.setOwner(ownerIndex);
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
		if (weapon != null) {
			weapon.setOwner(getOwner());
		}
	}

}
