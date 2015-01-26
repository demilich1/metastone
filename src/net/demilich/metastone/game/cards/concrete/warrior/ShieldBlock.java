package net.demilich.metastone.game.cards.concrete.warrior;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.BuffHeroSpell;
import net.demilich.metastone.game.spells.DrawCardSpell;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class ShieldBlock extends SpellCard {

	public ShieldBlock() {
		super("Shield Block", Rarity.FREE, HeroClass.WARRIOR, 3);
		setDescription("Gain 5 Armor. Draw a card.");
		setSpell(MetaSpell.create(BuffHeroSpell.create(0, 5), DrawCardSpell.create()));
		setTargetRequirement(TargetSelection.NONE);
	}


	@Override
	public int getTypeId() {
		return 378;
	}
}
