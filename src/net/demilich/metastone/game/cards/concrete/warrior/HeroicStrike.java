package net.demilich.metastone.game.cards.concrete.warrior;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.BuffHeroSpell;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class HeroicStrike extends SpellCard {

	public HeroicStrike() {
		super("Heroic Strike", Rarity.FREE, HeroClass.WARRIOR, 2);
		setDescription("Give your hero +4 Attack this turn.");
		setTargetRequirement(TargetSelection.NONE);
		setSpell(BuffHeroSpell.create(4, 0));
		setPredefinedTarget(EntityReference.FRIENDLY_HERO);
	}

	@Override
	public int getTypeId() {
		return 373;
	}
}
