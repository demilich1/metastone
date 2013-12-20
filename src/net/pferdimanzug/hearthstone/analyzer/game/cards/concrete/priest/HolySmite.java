package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.priest;

import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.EffectHint;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SingleTargetDamageSpell;

public class HolySmite extends SpellCard {

	public HolySmite() {
		super("Holy Smite", Rarity.FREE, HeroClass.PRIEST, 1);
		setTargetRequirement(TargetSelection.ANY);
		setEffectHint(EffectHint.NEGATIVE);
		setSpell(new SingleTargetDamageSpell(2));
	}

}
