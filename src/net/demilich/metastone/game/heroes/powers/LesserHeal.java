package net.demilich.metastone.game.heroes.powers;

import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.HealingSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class LesserHeal extends HeroPower {
	
	public static final int HEALING = +2;
	
	public LesserHeal() {
		super("Lesser Heal", HeroClass.PRIEST);
		setTargetRequirement(TargetSelection.ANY);
		setSpell(HealingSpell.create(2));
	}

}
