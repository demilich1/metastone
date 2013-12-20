package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warrior;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.WeaponCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.weapons.Weapon;

public class ArcaniteReaper extends WeaponCard {

	public ArcaniteReaper() {
		super("Arcanite Reaper", Rarity.FREE, HeroClass.WARRIOR, 5);
	}

	@Override
	public Weapon getWeapon() {
		return createWeapon(5, 2);
	}

}
