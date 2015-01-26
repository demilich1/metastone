package net.demilich.metastone.game.cards.concrete.tokens.weapons;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.WeaponCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.weapons.Weapon;

public class WickedKnife extends WeaponCard {

	public WickedKnife() {
		super("Wicked Knife", Rarity.FREE, HeroClass.ROGUE, 1);

		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 475;
	}

	@Override
	public Weapon getWeapon() {
		return createWeapon(1, 2);
	}
}
