package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.spells.trigger.TurnStartTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

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
		//TurnStartTrigger turnStartTrigger = new TurnStartTrigger(TargetPlayer.BOTH);
		TurnStartTrigger turnStartTrigger = new TurnStartTrigger(null);
		SpellDesc buffSpell = BuffSpell.create(EntityReference.SELF, 1, 0);
		SpellTrigger trigger = new SpellTrigger(turnStartTrigger, buffSpell);
		microMachine.setSpellTrigger(trigger);
		return microMachine;
	}
}
