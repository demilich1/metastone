package net.pferdimanzug.hearthstone.analyzer.game.heroes.powers;

import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffHeroSpell;

public class Shapeshift extends HeroPower {
	
	public Shapeshift() {
		super("Shapeshift");
		setTargetRequirement(TargetSelection.NONE);
		setSpell(new BuffHeroSpell(1, 1));
	}

}
