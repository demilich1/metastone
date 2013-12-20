package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.druid;

import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SingleTargetDamageSpell;

public class Moonfire extends SpellCard {

	public Moonfire() {
		super("Moonfire", Rarity.FREE, HeroClass.DRUID, 0);
		setSpell(new SingleTargetDamageSpell(1));
		setTargetRequirement(TargetSelection.ANY);
	}

}
