package net.demilich.metastone.game.heroes.powers;

import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class MindShatter extends HeroPower {

	public MindShatter() {
		super("Mind Shatter", HeroClass.PRIEST);
		setTargetRequirement(TargetSelection.ANY);
		setSpell(DamageSpell.create(3));
		
		setCollectible(false);
	}

}