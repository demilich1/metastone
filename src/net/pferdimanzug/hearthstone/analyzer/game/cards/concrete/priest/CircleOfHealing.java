package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.priest;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.HealingSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetKey;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class CircleOfHealing extends SpellCard {

	public CircleOfHealing() {
		super("Circle of Healing", Rarity.COMMON, HeroClass.PRIEST, 0);
		setSpell(new HealingSpell(4));
		setPredefinedTarget(TargetKey.ALL_MINIONS);
		setTargetRequirement(TargetSelection.NONE);
	}
	
}
