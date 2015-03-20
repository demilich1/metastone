package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.GameEventTrigger;
import net.demilich.metastone.game.spells.trigger.MinionSummonedTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

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
		SpellDesc buffSpell = BuffSpell.create(EntityReference.SELF, +1, 0);
		GameEventTrigger trigger = new MinionSummonedTrigger(TargetPlayer.BOTH, Race.MURLOC);
		murlocTideCaller.setSpellTrigger(new SpellTrigger(trigger, buffSpell));
		return murlocTideCaller;
	}
}
