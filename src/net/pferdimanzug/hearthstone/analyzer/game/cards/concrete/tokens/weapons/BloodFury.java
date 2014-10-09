package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.weapons;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.WeaponCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.weapons.Weapon;

public class BloodFury extends WeaponCard {

	public BloodFury() {
		super("Blood Fury", Rarity.COMMON, HeroClass.WARLOCK, 3);

		setCollectible(false);
	}

	@Override
	public Weapon getWeapon() {
		return createWeapon(3, 8);
	}

	@Override
	public int getTypeId() {
		return 473;
	}
}
