package net.pferdimanzug.hearthstone.analyzer.game.heroes.powers;

import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffHeroSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Shapeshift extends HeroPower {
	
	public Shapeshift() {
		super("Shapeshift");
		setTargetRequirement(TargetSelection.NONE);
		setSpell(new BuffHeroSpell(1, 1));
	}

}
