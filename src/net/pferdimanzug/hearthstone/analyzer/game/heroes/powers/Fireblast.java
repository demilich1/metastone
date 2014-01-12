package net.pferdimanzug.hearthstone.analyzer.game.heroes.powers;

import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Fireblast extends HeroPower {
	
	public static final int DAMAGE = 1;

	public Fireblast() {
		super("Fireblast");
		setTargetRequirement(TargetSelection.ANY);
		setSpell(new DamageSpell(1));
	}

}
