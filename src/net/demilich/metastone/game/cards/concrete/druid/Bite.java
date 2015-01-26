package net.demilich.metastone.game.cards.concrete.druid;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.BuffHeroSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Bite extends SpellCard {

	public Bite() {
		super("Bite", Rarity.RARE, HeroClass.DRUID, 4);
		setDescription("Give your hero +4 Attack this turn and 4 Armor.");
		setTargetRequirement(TargetSelection.NONE);
		setSpell(BuffHeroSpell.create(4, 4));
	}

	@Override
	public int getTypeId() {
		return 3;
	}
}
