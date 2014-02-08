package net.pferdimanzug.hearthstone.analyzer.game.heroes.powers;

import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffHeroSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Shapeshift extends HeroPower {
	
	public Shapeshift() {
		super("Shapeshift", HeroClass.DRUID);
		
		Spell buff = new BuffHeroSpell(+1, +1);
		buff.setTarget(EntityReference.FRIENDLY_HERO);
		setSpell(buff);
		setTargetRequirement(TargetSelection.NONE);
	}

}
