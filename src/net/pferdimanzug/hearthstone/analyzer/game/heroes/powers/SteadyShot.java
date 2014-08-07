package net.pferdimanzug.hearthstone.analyzer.game.heroes.powers;

import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class SteadyShot extends HeroPower {

	public static final int DAMAGE = 2;

	public SteadyShot() {
		super("Steady Shot", HeroClass.HUNTER);
		setTargetRequirement(TargetSelection.NONE);
		SpellDesc damage = DamageSpell.create(DAMAGE);
		damage.setTarget(EntityReference.ENEMY_HERO);
		setSpell(damage);
	}

}
