package net.demilich.metastone.game.cards.concrete.shaman;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.WeaponCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.weapons.Weapon;

public class StormforgedAxe extends WeaponCard {

	public StormforgedAxe() {
		super("Stormforged Axe", Rarity.COMMON, HeroClass.SHAMAN, 2);
		setDescription("Overload: (1)");
		setTag(GameTag.OVERLOAD, 1);
	}

	@Override
	public int getTypeId() {
		return 329;
	}

	@Override
	public Weapon getWeapon() {
		return createWeapon(2, 3);
	}
}
