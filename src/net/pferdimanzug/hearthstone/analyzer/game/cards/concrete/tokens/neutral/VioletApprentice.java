package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class VioletApprentice extends MinionCard {

	public VioletApprentice() {
		super("Violet Apprentice", 1, 1, Rarity.FREE, HeroClass.ANY, 0);
		setCollectible(false);
	}

	@Override
	public Minion summon() {
		return createMinion();
	}

}