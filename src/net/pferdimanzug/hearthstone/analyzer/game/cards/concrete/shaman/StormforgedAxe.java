package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.shaman;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.WeaponCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.weapons.Weapon;

public class StormforgedAxe extends WeaponCard {

	public StormforgedAxe() {
		super("Stormforged Axe", Rarity.COMMON, HeroClass.SHAMAN, 2);
		setDescription("Overload: (1)");
		setTag(GameTag.OVERLOAD, 1);
	}

	@Override
	public Weapon getWeapon() {
		return createWeapon(2, 3);
	}

}
