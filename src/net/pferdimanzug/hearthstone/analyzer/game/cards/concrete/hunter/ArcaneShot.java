package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.hunter;

import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetRequirement;
import net.pferdimanzug.hearthstone.analyzer.game.cards.EffectHint;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SingleTargetDamageSpell;

public class ArcaneShot extends SpellCard {

	public ArcaneShot() {
		super("Arcane Shot", Rarity.FREE, HeroClass.HUNTER, 1);
		setTargetRequirement(TargetRequirement.ANY);
		setEffectHint(EffectHint.NEGATIVE);
		setSpell(new SingleTargetDamageSpell(2));
	}

}
