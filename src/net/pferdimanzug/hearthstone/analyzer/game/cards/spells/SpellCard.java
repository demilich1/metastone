package net.pferdimanzug.hearthstone.analyzer.game.cards.spells;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardType;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;

public abstract class SpellCard extends Card {

	public SpellCard(String name, Rarity rarity, HeroClass classRestriction, int manaCost) {
		super(name, CardType.SPELL, rarity, classRestriction, manaCost);
	}

}
