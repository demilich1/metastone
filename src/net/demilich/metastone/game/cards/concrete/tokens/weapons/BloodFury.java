package net.demilich.metastone.game.cards.concrete.tokens.weapons;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.WeaponCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.weapons.Weapon;

public class BloodFury extends WeaponCard {

	public BloodFury() {
		super("Blood Fury", Rarity.COMMON, HeroClass.WARLOCK, 3);

		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 473;
	}

	@Override
	public Weapon getWeapon() {
		return createWeapon(3, 8);
	}
}
