package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warrior;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.WeaponCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.weapons.Weapon;

public class FieryWarAxe extends WeaponCard {

	public FieryWarAxe() {
		super("Fiery War Axe", Rarity.FREE, HeroClass.WARRIOR, 2);
	}

	@Override
	public Weapon getWeapon() {
		return createWeapon(3, 2);
	}

}
