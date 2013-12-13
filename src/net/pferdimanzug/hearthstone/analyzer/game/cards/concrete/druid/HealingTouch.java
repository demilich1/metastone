package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.druid;

import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetRequirement;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SingleTargetHealingSpell;

public class HealingTouch extends SpellCard {

	public HealingTouch() {
		super("Healing Touch", Rarity.FREE, HeroClass.DRUID, 3);
		setTargetRequirement(TargetRequirement.ANY);
		setSpell(new SingleTargetHealingSpell(8));
	}

}
