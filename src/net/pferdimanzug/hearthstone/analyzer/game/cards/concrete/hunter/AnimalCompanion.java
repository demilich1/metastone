package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.hunter;

import net.pferdimanzug.hearthstone.analyzer.game.actions.PlayCardAction;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.spells.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;

public class AnimalCompanion extends SpellCard {

	public AnimalCompanion(String name, Rarity rarity, HeroClass classRestriction, int manaCost) {
		super(name, rarity, classRestriction, manaCost);
	}

	@Override
	public PlayCardAction play() {
		// TODO Auto-generated method stub
		return null;
	}

}
