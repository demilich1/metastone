package net.demilich.metastone.game.heroes.powers;

import net.demilich.metastone.game.cards.concrete.tokens.weapons.WickedKnife;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.EquipWeaponSpell;
import net.demilich.metastone.game.targeting.TargetSelection;


public class DaggerMastery extends HeroPower {

	public DaggerMastery() {
		super("Dagger Mastery", HeroClass.ROGUE);
		setTargetRequirement(TargetSelection.NONE);
		setSpell(EquipWeaponSpell.create(new WickedKnife()));
	}


}
