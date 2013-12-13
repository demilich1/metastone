package net.pferdimanzug.hearthstone.analyzer.game.heroes.powers;

import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetRequirement;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SingleTargetDamageSpell;

public class SteadyShot extends HeroPower {

	public static final int DAMAGE = 2;

	public SteadyShot() {
		super("Steady Shot");
		setTargetRequirement(TargetRequirement.ENEMY_HERO);
		setSpell(new SingleTargetDamageSpell(DAMAGE));
	}

}
