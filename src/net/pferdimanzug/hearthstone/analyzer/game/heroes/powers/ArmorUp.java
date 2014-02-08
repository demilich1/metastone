package net.pferdimanzug.hearthstone.analyzer.game.heroes.powers;

import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffHeroSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class ArmorUp extends HeroPower {

	public static final int ARMOR_BONUS = +2;

	public ArmorUp() {
		super("Armor Up!", HeroClass.WARRIOR);
		setTargetRequirement(TargetSelection.NONE);
		setSpell(new BuffHeroSpell(0, ARMOR_BONUS));
		setPredefinedTarget(EntityReference.FRIENDLY_HERO);
	}

}
