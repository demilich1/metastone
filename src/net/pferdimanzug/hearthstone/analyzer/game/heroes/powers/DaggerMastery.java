package net.pferdimanzug.hearthstone.analyzer.game.heroes.powers;

import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.weapons.WickedKnife;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.EquipWeaponSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;


public class DaggerMastery extends HeroPower {

	public DaggerMastery() {
		super("Dagger Mastery", HeroClass.ROGUE);
		setTargetRequirement(TargetSelection.NONE);
		setSpell(EquipWeaponSpell.create(new WickedKnife()));
		setPredefinedTarget(EntityReference.FRIENDLY_HERO);
	}


}
