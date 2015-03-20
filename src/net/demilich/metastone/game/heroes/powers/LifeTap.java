package net.demilich.metastone.game.heroes.powers;

import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.DrawCardSpell;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class LifeTap extends HeroPower {

	public static final int DAMAGE = 2;

	public LifeTap() {
		super("Life Tap", HeroClass.WARLOCK);

		SpellDesc damage = DamageSpell.create(EntityReference.FRIENDLY_HERO, 2);
		SpellDesc draw = DrawCardSpell.create();
		setSpell(MetaSpell.create(EntityReference.NONE, damage, draw));
		setTargetRequirement(TargetSelection.NONE);
	}

}
