package net.demilich.metastone.game.cards.concrete.warrior;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.WeaponCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.weapons.Weapon;

public class FieryWarAxe extends WeaponCard {

	public FieryWarAxe() {
		super("Fiery War Axe", Rarity.FREE, HeroClass.WARRIOR, 2);
	}

	@Override
	public int getTypeId() {
		return 369;
	}



	@Override
	public Weapon getWeapon() {
		return createWeapon(3, 2);
	}
}
