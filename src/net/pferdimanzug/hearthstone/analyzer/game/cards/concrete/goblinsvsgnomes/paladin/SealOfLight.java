package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.paladin;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffHeroSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.HealingSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

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
