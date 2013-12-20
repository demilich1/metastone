package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warlock;

import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SingleTargetDamageSpell;

public class ShadowBolt extends SpellCard {

	public ShadowBolt() {
		super("Shadow Bolt", Rarity.FREE, HeroClass.WARLOCK, 3);
		setSpell(new SingleTargetDamageSpell(4));
		setTargetRequirement(TargetSelection.MINIONS);
	}

}
