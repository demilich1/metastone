package net.pferdimanzug.hearthstone.analyzer.game.heroes.powers;

import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class MindShatter extends HeroPower {

	public MindShatter() {
		super("Mind Shatter", HeroClass.PRIEST);
		setTargetRequirement(TargetSelection.ANY);
		setSpell(DamageSpell.create(3));
		
		setCollectible(false);
	}

}