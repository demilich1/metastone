package net.demilich.metastone.game.heroes.powers;

import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class DieInsect extends HeroPower {

	public DieInsect() {
		super("DIE, INSECT", HeroClass.ANY);
		setSpell(DamageSpell.create(EntityReference.ENEMY_CHARACTERS, 8, true));
		setTargetRequirement(TargetSelection.NONE);

		setCollectible(false);
	}

}
