package net.pferdimanzug.hearthstone.analyzer.game.heroes.powers;

import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetRequirement;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SingleTargetDamageSpell;

public class Fireblast extends HeroPower {
	
	public static final int DAMAGE = 1;

	public Fireblast() {
		super("Fireblast");
		setTargetRequirement(TargetRequirement.ANY);
		setSpell(new SingleTargetDamageSpell(1));
	}

}
