package net.demilich.metastone.game.heroes.powers;

import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.BuffHeroSpell;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class ArmorUp extends HeroPower {

	public static final int ARMOR_BONUS = +2;

	public ArmorUp() {
		super("Armor Up!", HeroClass.WARRIOR);
		setTargetRequirement(TargetSelection.NONE);
		setSpell(BuffHeroSpell.create(EntityReference.FRIENDLY_HERO, 0, ARMOR_BONUS));
	}

}
