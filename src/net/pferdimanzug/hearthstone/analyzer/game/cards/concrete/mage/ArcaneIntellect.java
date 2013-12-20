package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.mage;

import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardSpell;

public class ArcaneIntellect extends SpellCard {

	public ArcaneIntellect() {
		super("Arcane Intellect", Rarity.FREE, HeroClass.MAGE, 3);
		setTargetRequirement(TargetSelection.NONE);
		setSpell(new DrawCardSpell(2));
	}

}
