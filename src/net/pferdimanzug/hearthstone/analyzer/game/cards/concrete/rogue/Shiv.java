package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.rogue;

import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageAndDrawCardSpell;

public class Shiv extends SpellCard {

	public Shiv(String name, Rarity rarity, HeroClass classRestriction, int manaCost) {
		super(name, rarity, classRestriction, manaCost);
		setSpell(new DamageAndDrawCardSpell(1));
		setTargetRequirement(TargetSelection.ANY);
	}

}
