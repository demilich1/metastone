package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.druid;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.GainManaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Innervate extends SpellCard {

	public Innervate() {
		super("Innervate", Rarity.FREE, HeroClass.DRUID, 0);
		setTargetRequirement(TargetSelection.NONE);
		setSpell(new GainManaSpell(2));
	}

}
