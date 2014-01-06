package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.rogue;

import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardSpell;

public class Sprint extends SpellCard {

	public Sprint() {
		super("Sprint", Rarity.FREE, HeroClass.ROGUE, 7);
		setSpell(new DrawCardSpell(4));
		setTargetRequirement(TargetSelection.NONE);
	}

}
