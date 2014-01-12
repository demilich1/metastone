package net.pferdimanzug.hearthstone.analyzer.game.heroes.powers;

import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffHeroSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetKey;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class ArmorUp extends HeroPower {

	public static final int ARMOR_BONUS = +2;

	public ArmorUp() {
		super("Armor Up!");
		setTargetRequirement(TargetSelection.NONE);
		setSpell(new BuffHeroSpell(0, ARMOR_BONUS));
		setPredefinedTarget(TargetKey.FRIENDLY_HERO);
	}

}
