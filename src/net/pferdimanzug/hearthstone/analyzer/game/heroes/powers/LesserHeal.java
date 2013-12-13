package net.pferdimanzug.hearthstone.analyzer.game.heroes.powers;

import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetRequirement;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SingleTargetHealingSpell;

public class LesserHeal extends HeroPower {
	
	public static final int HEALING = +2;
	
	public LesserHeal() {
		super("Lesser Heal");
		setTargetRequirement(TargetRequirement.ANY);
		setSpell(new SingleTargetHealingSpell(HEALING));
	}

}
