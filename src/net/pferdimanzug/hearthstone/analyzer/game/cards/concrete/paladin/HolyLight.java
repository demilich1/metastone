package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.paladin;

import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SingleTargetHealingSpell;

public class HolyLight extends SpellCard {

	public HolyLight() {
		super("Holy Light", Rarity.FREE, HeroClass.PALADIN, 2);
		setTargetRequirement(TargetSelection.ANY);
		setSpell(new SingleTargetHealingSpell(6));
	}

}
