package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.shaman;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.WeaponCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.entities.weapons.Weapon;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.filter.RaceFilter;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

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
		SpellDesc buffMech = BuffSpell.create(+2, +2);
		buffMech.setTarget(EntityReference.FRIENDLY_MINIONS);
		buffMech.setTargetFilter(new RaceFilter(Race.MECH));
		buffMech.pickRandomTarget(true);
		powermace.addDeathrattle(buffMech);
		return powermace;
	}
}
