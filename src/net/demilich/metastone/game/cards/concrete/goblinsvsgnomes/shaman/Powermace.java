package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.shaman;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.WeaponCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.weapons.Weapon;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class Powermace extends WeaponCard {

	public Powermace() {
		super("Powermace", Rarity.RARE, HeroClass.SHAMAN, 3);
		setDescription("Deathrattle: Give a random friendly Mech +2/+2.");
	}

	@Override
	public int getTypeId() {
		return 579;
	}

	@Override
	public Weapon getWeapon() {
		Weapon powermace = createWeapon(3, 2);
		//SpellDesc buffMech = BuffSpell.create(EntityReference.FRIENDLY_MINIONS, +2, +2, new RaceFilter(Race.MECH), true);
		SpellDesc buffMech = BuffSpell.create(EntityReference.FRIENDLY_MINIONS, +2, +2, null, true);
		powermace.addDeathrattle(buffMech);
		return powermace;
	}
}
