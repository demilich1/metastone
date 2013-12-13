package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.rogue;

import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetRequirement;
import net.pferdimanzug.hearthstone.analyzer.game.cards.EffectHint;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DestroySpell;

public class Assassinate extends SpellCard {

	public Assassinate() {
		super("Assasinate", Rarity.FREE, HeroClass.ROGUE, 5);
		setTargetRequirement(TargetRequirement.ENEMY_MINIONS);
		setEffectHint(EffectHint.NEGATIVE);
		setSpell(new DestroySpell());
	}
	
}
