package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.warrior;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.ArmorGainedTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class SiegeEngine extends MinionCard {

	public SiegeEngine() {
		super("Siege Engine", 5, 5, Rarity.RARE, HeroClass.WARRIOR, 5);
		setDescription("Whenever you gain Armor, give this minion +1 Attack.");
		setRace(Race.MECH);
	}

	@Override
	public Minion summon() {
		Minion siegeEngine = createMinion();
		SpellDesc gainAttack = BuffSpell.create(+1);
		gainAttack.setTarget(EntityReference.SELF);
		SpellTrigger trigger = new SpellTrigger(new ArmorGainedTrigger(), gainAttack);
		siegeEngine.setSpellTrigger(trigger);
		return siegeEngine;
	}

}
