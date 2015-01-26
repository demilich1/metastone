package net.demilich.metastone.game.cards.concrete.mage;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DrawCardSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class ArcaneIntellect extends SpellCard {

	public ArcaneIntellect() {
		super("Arcane Intellect", Rarity.FREE, HeroClass.MAGE, 3);
		setDescription("Draw 2 cards.");
		setTargetRequirement(TargetSelection.NONE);
		setSpell(DrawCardSpell.create(2));
	}

	@Override
	public int getTypeId() {
		return 52;
	}
}
