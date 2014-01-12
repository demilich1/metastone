package net.pferdimanzug.hearthstone.analyzer.game.heroes.powers;

import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class SteadyShot extends HeroPower {

	public static final int DAMAGE = 2;

	public SteadyShot() {
		super("Steady Shot");
		setTargetRequirement(TargetSelection.ENEMY_HERO);
		setSpell(new DamageSpell(DAMAGE));
	}

}
