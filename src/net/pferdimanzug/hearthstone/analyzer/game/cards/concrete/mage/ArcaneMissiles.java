package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.mage;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.spells.RandomSpellDamageCard;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;

public class ArcaneMissiles extends RandomSpellDamageCard {

	public ArcaneMissiles() {
		super("Arcane Missiles", Rarity.FREE, HeroClass.MAGE, 1, 1, 3);
	}
}
