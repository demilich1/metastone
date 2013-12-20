package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.paladin;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.WeaponCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.weapons.Weapon;

public class LightsJustice extends WeaponCard{

	public LightsJustice() {
		super("Light's Justice", Rarity.FREE, HeroClass.PALADIN, 1);
	}

	@Override
	public Weapon getWeapon() {
		return createWeapon(1, 4);
	}

}
