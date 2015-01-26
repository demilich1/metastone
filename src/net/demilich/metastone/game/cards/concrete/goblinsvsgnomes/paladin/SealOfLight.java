package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.paladin;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.BuffHeroSpell;
import net.demilich.metastone.game.spells.HealingSpell;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class SealOfLight extends SpellCard {

	public SealOfLight() {
		super("Seal of Light", Rarity.COMMON, HeroClass.PALADIN, 2);
		setDescription("Restore 4 Health to your hero and gain +2 Attack this turn.");

		SpellDesc heal = HealingSpell.create(4);
		SpellDesc attack = BuffHeroSpell.create(+2, 0);
		setSpell(MetaSpell.create(heal, attack));
		setTargetRequirement(TargetSelection.NONE);
		setPredefinedTarget(EntityReference.FRIENDLY_HERO);

	}



	@Override
	public int getTypeId() {
		return 557;
	}
}
