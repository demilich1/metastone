package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.mage;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class ArcaneIntellect extends SpellCard {

	public ArcaneIntellect() {
		super("Arcane Intellect", Rarity.FREE, HeroClass.MAGE, 3);
		setDescription("Draw 2 cards.");
		setTargetRequirement(TargetSelection.NONE);
		setSpell(new DrawCardSpell(2));
	}

	@Override
	public int getTypeId() {
		return 52;
	}
}
