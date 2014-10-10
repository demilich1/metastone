package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.weapons;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.WeaponCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.weapons.Weapon;

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
