package net.demilich.metastone.game.cards.concrete.paladin;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.WeaponCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.weapons.Weapon;

public class LightsJustice extends WeaponCard{

	public LightsJustice() {
		super("Light's Justice", Rarity.FREE, HeroClass.PALADIN, 1);
	}

	@Override
	public int getTypeId() {
		return 252;
	}

	@Override
	public Weapon getWeapon() {
		return createWeapon(1, 4);
	}
}
