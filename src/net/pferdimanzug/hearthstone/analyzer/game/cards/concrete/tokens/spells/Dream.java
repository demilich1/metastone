package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.spells;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ReturnMinionToHandSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Dream extends SpellCard {

	public Dream() {
		super("Dream", Rarity.FREE, HeroClass.ANY, 0);
		setDescription("Return a minion to its owners's hand.");
		setCollectible(false);

		setSpell(ReturnMinionToHandSpell.create());
		setTargetRequirement(TargetSelection.MINIONS);
	}

}