package net.demilich.metastone.game.cards.concrete.tokens.weapons;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.WeaponCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.weapons.Weapon;

public class Ashbringer extends WeaponCard {

	public Ashbringer() {
		super("Ashbringer", Rarity.LEGENDARY, HeroClass.PALADIN, 5);

		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 471;
	}

	@Override
	public Weapon getWeapon() {
		return createWeapon(5, 3);
	}
}
