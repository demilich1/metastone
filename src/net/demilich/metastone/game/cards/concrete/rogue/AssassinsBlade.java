package net.demilich.metastone.game.cards.concrete.rogue;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.WeaponCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.weapons.Weapon;

public class AssassinsBlade extends WeaponCard {

	public AssassinsBlade() {
		super("Assassin's Blade", Rarity.FREE, HeroClass.ROGUE, 5);
	}

	@Override
	public int getTypeId() {
		return 285;
	}



	@Override
	public Weapon getWeapon() {
		return createWeapon(3, 4);
	}
}
