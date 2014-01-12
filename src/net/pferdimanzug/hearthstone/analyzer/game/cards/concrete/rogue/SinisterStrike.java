package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.rogue;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class SinisterStrike extends SpellCard {

	public SinisterStrike() {
		super("Sinister Strike", Rarity.FREE, HeroClass.ROGUE, 1);
		setSpell(new DamageSpell(3));
		setTargetRequirement(TargetSelection.ENEMY_HERO);
	}

}
