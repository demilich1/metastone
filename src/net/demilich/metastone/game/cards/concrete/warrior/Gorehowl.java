package net.demilich.metastone.game.cards.concrete.warrior;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.WeaponCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.weapons.Weapon;


public class Gorehowl extends WeaponCard {

	public Gorehowl() {
		super("Gorehowl", Rarity.EPIC, HeroClass.WARRIOR, 7);
		setDescription("Attacking a minion costs 1 Attack instead of 1 Durability.");
	}

	@Override
	public int getTypeId() {
		return 371;
	}

	@Override
	public Weapon getWeapon() {
		Weapon gorehowl = createWeapon(7, 1);
		gorehowl.setTag(GameTag.CONSUME_DAMAGE_INSTEAD_OF_DURABILITY_ON_MINIONS);
		return gorehowl;
	}
}
