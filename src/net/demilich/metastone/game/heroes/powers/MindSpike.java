package net.demilich.metastone.game.heroes.powers;

import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class MindSpike extends HeroPower {

	public MindSpike() {
		super("Mind Spike", HeroClass.PRIEST);
		setTargetRequirement(TargetSelection.ANY);
		setSpell(DamageSpell.create(2));
		
		setCollectible(false);
	}

}