package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnStartTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class MicroMachine extends MinionCard {

	public MicroMachine() {
		super("Micro Machine", 1, 2, Rarity.COMMON, HeroClass.ANY, 2);
		setDescription("At the start of each turn, gain +1 Attack.");
		setRace(Race.MECH);
	}

	@Override
	public int getTypeId() {
		return 533;
	}



	@Override
	public Minion summon() {
		Minion microMachine = createMinion();
		TurnStartTrigger turnStartTrigger = new TurnStartTrigger(TargetPlayer.BOTH);
		SpellDesc buffSpell = BuffSpell.create(1);
		buffSpell.setTarget(EntityReference.SELF);
		SpellTrigger trigger = new SpellTrigger(turnStartTrigger, buffSpell);
		microMachine.setSpellTrigger(trigger);
		return microMachine;
	}
}
