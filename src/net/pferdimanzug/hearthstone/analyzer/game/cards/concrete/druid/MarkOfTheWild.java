package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.druid;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ApplyTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class MarkOfTheWild extends SpellCard {

	public MarkOfTheWild() {
		super("Mark of the Wild", Rarity.FREE, HeroClass.DRUID, 2);
		setSpell(new MetaSpell(new BuffSpell(2, 2), new ApplyTagSpell(GameTag.TAUNT)));
		setTargetRequirement(TargetSelection.MINIONS);
	}

}
