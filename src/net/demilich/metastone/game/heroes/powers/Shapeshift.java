package net.demilich.metastone.game.heroes.powers;

import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.BuffHeroSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Shapeshift extends HeroPower {
	
	public Shapeshift() {
		super("Shapeshift", HeroClass.DRUID);
		
		SpellDesc buff = BuffHeroSpell.create(+1, +1);
		setSpell(buff);
		setTargetRequirement(TargetSelection.NONE);
	}

}
