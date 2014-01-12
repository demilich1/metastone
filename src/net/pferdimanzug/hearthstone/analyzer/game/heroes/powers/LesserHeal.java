package net.pferdimanzug.hearthstone.analyzer.game.heroes.powers;

import net.pferdimanzug.hearthstone.analyzer.game.spells.HealingSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class LesserHeal extends HeroPower {
	
	public static final int HEALING = +2;
	
	public LesserHeal() {
		super("Lesser Heal");
		setTargetRequirement(TargetSelection.ANY);
		setSpell(new HealingSpell(HEALING));
	}

}
