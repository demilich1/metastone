package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffWhenRaceIsOnBoardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.BoardChangedTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

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
