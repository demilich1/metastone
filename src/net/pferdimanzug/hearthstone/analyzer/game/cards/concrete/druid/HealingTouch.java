package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.druid;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.HealingSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class HealingTouch extends SpellCard {

	public HealingTouch() {
		super("Healing Touch", Rarity.FREE, HeroClass.DRUID, 3);
		setDescription("Restore #8 Health.");
		setTargetRequirement(TargetSelection.ANY);
		setSpell(new HealingSpell(8));
	}

}
