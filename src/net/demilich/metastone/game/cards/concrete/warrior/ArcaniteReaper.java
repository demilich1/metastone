package net.demilich.metastone.game.cards.concrete.warrior;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.WeaponCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.weapons.Weapon;

public class ArcaniteReaper extends WeaponCard {

	public ArcaniteReaper() {
		super("Arcanite Reaper", Rarity.FREE, HeroClass.WARRIOR, 5);
	}

	@Override
	public int getTypeId() {
		return 360;
	}



	@Override
	public Weapon getWeapon() {
		return createWeapon(5, 2);
	}
}
