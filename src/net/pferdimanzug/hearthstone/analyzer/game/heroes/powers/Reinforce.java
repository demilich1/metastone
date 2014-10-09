package net.pferdimanzug.hearthstone.analyzer.game.heroes.powers;

import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.paladin.SilverHandRecruit;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Reinforce extends HeroPower {

	public Reinforce() {
		super("Reinforce", HeroClass.PALADIN);
		setTargetRequirement(TargetSelection.NONE);
		setSpell(SummonSpell.create(new SilverHandRecruit()));
	}

}
