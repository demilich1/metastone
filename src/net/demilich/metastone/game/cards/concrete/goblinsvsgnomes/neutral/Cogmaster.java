package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.BuffWhenRaceIsOnBoardSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.BoardChangedTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class Cogmaster extends MinionCard {

	public Cogmaster() {
		super("Cogmaster", 1, 2, Rarity.COMMON, HeroClass.ANY, 1);
		setDescription("Has +2 Attack while you have a Mech.");
	}

	@Override
	public int getTypeId() {
		return 508;
	}



	@Override
	public Minion summon() {
		Minion cogmaster = createMinion();
		SpellDesc buffSpell = BuffWhenRaceIsOnBoardSpell.create(Race.MECH, +2);
		buffSpell.setTarget(EntityReference.SELF);
		SpellTrigger trigger = new SpellTrigger(new BoardChangedTrigger(), buffSpell);
		cogmaster.setSpellTrigger(trigger);
		return cogmaster;
	}
}
