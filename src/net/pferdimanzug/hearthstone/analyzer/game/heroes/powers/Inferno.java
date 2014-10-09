package net.pferdimanzug.hearthstone.analyzer.game.heroes.powers;

import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.warlock.Infernal;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Inferno extends HeroPower {

	public Inferno() {
		super("INFERNO!", HeroClass.WARLOCK);
		setSpell(SummonSpell.create(new Infernal()));
		setTargetRequirement(TargetSelection.NONE);
		
		setCollectible(false);
	}

}