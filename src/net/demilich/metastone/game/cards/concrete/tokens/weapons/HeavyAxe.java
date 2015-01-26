package net.demilich.metastone.game.cards.concrete.tokens.weapons;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.WeaponCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.weapons.Weapon;

public class HeavyAxe extends WeaponCard {

	public HeavyAxe() {
		super("Heavy Axe", Rarity.RARE, HeroClass.WARRIOR, 1);

		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 474;
	}

	@Override
	public Weapon getWeapon() {
		return createWeapon(1, 3);
	}
}
