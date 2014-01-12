package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.druid;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Moonfire extends SpellCard {

	public Moonfire() {
		super("Moonfire", Rarity.FREE, HeroClass.DRUID, 0);
		setSpell(new DamageSpell(1));
		setTargetRequirement(TargetSelection.ANY);
	}

}
