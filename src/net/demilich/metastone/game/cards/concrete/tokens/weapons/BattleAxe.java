package net.demilich.metastone.game.cards.concrete.tokens.weapons;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.WeaponCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.weapons.Weapon;

public class BattleAxe extends WeaponCard {

	public BattleAxe() {
		super("Battle Axe", Rarity.FREE, HeroClass.WARRIOR, 1);

		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 472;
	}

	@Override
	public Weapon getWeapon() {
		return createWeapon(2, 2);
	}
}
