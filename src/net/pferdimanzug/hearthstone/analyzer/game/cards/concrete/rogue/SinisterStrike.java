package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.rogue;

import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SingleTargetDamageSpell;

public class SinisterStrike extends SpellCard {

	public SinisterStrike() {
		super("Sinister Strike", Rarity.FREE, HeroClass.ROGUE, 1);
		setSpell(new SingleTargetDamageSpell(3));
		setTargetRequirement(TargetSelection.ENEMY_HERO);
	}

}
