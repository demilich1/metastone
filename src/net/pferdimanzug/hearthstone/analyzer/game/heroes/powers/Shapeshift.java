package net.pferdimanzug.hearthstone.analyzer.game.heroes.powers;

import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffHeroSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Shapeshift extends HeroPower {
	
	public Shapeshift() {
		super("Shapeshift", HeroClass.DRUID);
		
		SpellDesc buff = BuffHeroSpell.create(+1, +1);
		setSpell(buff);
		setTargetRequirement(TargetSelection.NONE);
	}

}
