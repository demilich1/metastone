package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.MinionDeathTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class Junkbot extends MinionCard {

	public Junkbot() {
		super("Junkbot", 1, 5, Rarity.EPIC, HeroClass.ANY, 5);
		setDescription("Whenever a friendly Mech dies, gain +2/+2.");
		setRace(Race.MECH);
	}

	@Override
	public int getTypeId() {
		return 525;
	}



	@Override
	public Minion summon() {
		Minion junkbot = createMinion();
		SpellDesc buff = BuffSpell.create(2, 2);
		buff.setTarget(EntityReference.SELF);
		SpellTrigger trigger = new SpellTrigger(new MinionDeathTrigger(TargetPlayer.SELF, Race.MECH), buff);
		junkbot.setSpellTrigger(trigger);
		return junkbot;
	}
}
