package net.demilich.metastone.game.cards.concrete.warlock;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.HealingSpell;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class DrainLife extends SpellCard {

	public DrainLife() {
		super("Drain Life", Rarity.FREE, HeroClass.WARLOCK, 3);
		setDescription("Deal $2 damage. Restore #2 Health to your hero.");
		SpellDesc damage = DamageSpell.create(2);
		SpellDesc heal = HealingSpell.create(EntityReference.FRIENDLY_HERO, 2);
		setSpell(MetaSpell.create(damage, heal));
		setTargetRequirement(TargetSelection.ANY);
	}

	@Override
	public int getTypeId() {
		return 339;
	}
}
