package net.pferdimanzug.hearthstone.analyzer.game.heroes.powers;

import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffHeroArmorSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffHeroAttackSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;

public class Shapeshift extends HeroPower {
	
	public Shapeshift() {
		super("Shapeshift");
		setTargetRequirement(TargetSelection.NONE);
		setSpell(new MetaSpell(new BuffHeroAttackSpell(1), new BuffHeroArmorSpell(1)));
	}

}
