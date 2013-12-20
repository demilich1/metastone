package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.priest;

import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.AreaHealingSpell;

public class CircleOfHealing extends SpellCard {

	public CircleOfHealing() {
		super("Circle of Healing", Rarity.COMMON, HeroClass.PRIEST, 0);
		setSpell(new AreaHealingSpell(4, TargetSelection.MINIONS));
		setTargetRequirement(TargetSelection.NONE);
	}
	
}
