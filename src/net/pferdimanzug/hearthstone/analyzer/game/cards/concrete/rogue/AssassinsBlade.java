package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.rogue;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.WeaponCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.weapons.Weapon;

public class AssassinsBlade extends WeaponCard {

	public AssassinsBlade() {
		super("Assassin's Blade", Rarity.FREE, HeroClass.ROGUE, 5);
	}

	@Override
	public Weapon getWeapon() {
		return createWeapon(3, 4);
	}

}
