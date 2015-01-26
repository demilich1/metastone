package net.demilich.metastone.game.cards.concrete.priest;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.HealingSpell;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class HolyFire extends SpellCard {

	public HolyFire() {
		super("Holy Fire", Rarity.RARE, HeroClass.PRIEST, 6);
		setDescription("Deal $5 damage. Restore 5 Health to your hero.");

		SpellDesc damageSpell = DamageSpell.create(5);
		SpellDesc healHeroSpell = HealingSpell.create(5);
		healHeroSpell.setTarget(EntityReference.FRIENDLY_HERO);

		setSpell(MetaSpell.create(damageSpell, healHeroSpell));
		setTargetRequirement(TargetSelection.ANY);
	}

	@Override
	public int getTypeId() {
		return 263;
	}
}
