package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.druid;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;

public class MarkOfTheWild extends SpellCard {

	public MarkOfTheWild() {
		super("Mark of the Wild", Rarity.FREE, HeroClass.DRUID, 2);
		setSpell(new BuffSpell(2, 2, GameTag.TAUNT));
		setTargetRequirement(TargetSelection.MINIONS);
	}

}
