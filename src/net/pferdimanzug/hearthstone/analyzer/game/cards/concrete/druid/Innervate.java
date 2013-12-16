package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.druid;

import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetRequirement;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.GainManaSpell;

public class Innervate extends SpellCard {

	public Innervate() {
		super("Innervate", Rarity.FREE, HeroClass.DRUID, 0);
		setTargetRequirement(TargetRequirement.NONE);
		setSpell(new GainManaSpell(2));
	}

}
