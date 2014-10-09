package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.hunter;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;

public class Snake extends MinionCard {

	public Snake() {
		super("Snake", 1, 1, Rarity.COMMON, HeroClass.HUNTER, 0);
		setRace(Race.BEAST);
		setCollectible(false);
	}

	@Override
	public Minion summon() {
		return createMinion();
	}
	
}