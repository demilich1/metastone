package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;

public class Chicken extends MinionCard {

	public Chicken() {
		super("Chicken", 1, 1, Rarity.FREE, HeroClass.ANY, 0);
		setDescription("Hey Chicken!");
		setRace(Race.BEAST);
		setCollectible(false);
	}

	@Override
	public Minion summon() {
		return createMinion();
	}

}