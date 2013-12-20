package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.paladin;

import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.EffectHint;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;

public class BlessingOfMight extends SpellCard {

	public BlessingOfMight() {
		super("Blessing of Might", Rarity.FREE, HeroClass.PALADIN, 1);
		setTargetRequirement(TargetSelection.MINIONS);
		setEffectHint(EffectHint.POSITIVE);
		setSpell(new BuffSpell(3, 0));
	}
}
