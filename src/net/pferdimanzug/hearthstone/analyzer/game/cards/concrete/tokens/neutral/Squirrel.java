package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;

public class Squirrel extends MinionCard {

	public Squirrel() {
		super("Squirrel", 1, 1, Rarity.COMMON, HeroClass.ANY, 1);
		setRace(Race.BEAST);
		setCollectible(false);
	}

	@Override
	public Minion summon() {
		return createMinion();
	}

}