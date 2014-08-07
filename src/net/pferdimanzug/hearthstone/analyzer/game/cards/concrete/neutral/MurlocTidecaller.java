package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.GameEventTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.MinionSummonedTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class MurlocTidecaller extends MinionCard {

	public MurlocTidecaller() {
		super("Murloc Tidecaller", 1, 2, Rarity.RARE, HeroClass.ANY, 1);
		setDescription("Whenever a Murloc is summoned, gain +1 Attack.");
		setRace(Race.MURLOC);
	}

	@Override
	public int getTypeId() {
		return 171;
	}

	@Override
	public Minion summon() {
		Minion murlocTideCaller = createMinion();
		SpellDesc buffSpell = BuffSpell.create(1);
		buffSpell.setTarget(EntityReference.SELF);
		GameEventTrigger trigger = new MinionSummonedTrigger(TargetPlayer.BOTH, Race.MURLOC);
		murlocTideCaller.setSpellTrigger(new SpellTrigger(trigger, buffSpell));
		return murlocTideCaller;
	}
}
