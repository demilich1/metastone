package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.paladin;

import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetRequirement;
import net.pferdimanzug.hearthstone.analyzer.game.cards.EffectHint;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;

public class BlessingOfMight extends SpellCard {

	public BlessingOfMight() {
		super("Blessing of Might", Rarity.FREE, HeroClass.PALADIN, 1);
		setTargetRequirement(TargetRequirement.MINIONS);
		setEffectHint(EffectHint.POSITIVE);
		setSpell(new BuffSpell(3, 0));
	}
}
