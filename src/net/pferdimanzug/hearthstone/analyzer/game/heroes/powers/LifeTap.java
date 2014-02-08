package net.pferdimanzug.hearthstone.analyzer.game.heroes.powers;

import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class LifeTap extends HeroPower {

	public static final int DAMAGE = 2;
	
	public LifeTap() {
		super("Life Tap", HeroClass.WARLOCK);
		setTargetRequirement(TargetSelection.NONE);
		Spell damage = new DamageSpell(2);
		damage.setTarget(EntityReference.FRIENDLY_HERO);
		Spell draw = new DrawCardSpell();
		setSpell(new MetaSpell(damage, draw));
	}

}
