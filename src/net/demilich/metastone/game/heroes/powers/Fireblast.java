package net.demilich.metastone.game.heroes.powers;

import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Fireblast extends HeroPower {
	
	public static final int DAMAGE = 1;

	public Fireblast() {
		super("Fireblast", HeroClass.MAGE);
		setTargetRequirement(TargetSelection.ANY);
		setSpell(DamageSpell.create(1));
	}

}
