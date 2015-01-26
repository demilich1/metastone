package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.MinionDeathTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

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
