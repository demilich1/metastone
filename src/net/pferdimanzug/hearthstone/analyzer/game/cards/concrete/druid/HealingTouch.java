package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.druid;

import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SingleTargetHealingSpell;

public class HealingTouch extends SpellCard {

	public HealingTouch() {
		super("Healing Touch", Rarity.FREE, HeroClass.DRUID, 3);
		setTargetRequirement(TargetSelection.ANY);
		setSpell(new SingleTargetHealingSpell(8));
	}

}
