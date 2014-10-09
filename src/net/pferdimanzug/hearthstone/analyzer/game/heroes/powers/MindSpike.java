package net.pferdimanzug.hearthstone.analyzer.game.heroes.powers;

import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class MindSpike extends HeroPower {

	public MindSpike() {
		super("Mind Spike", HeroClass.PRIEST);
		setTargetRequirement(TargetSelection.ANY);
		setSpell(DamageSpell.create(2));
		
		setCollectible(false);
	}

}