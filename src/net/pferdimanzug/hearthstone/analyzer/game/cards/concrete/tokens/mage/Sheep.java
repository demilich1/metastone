package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.mage;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;

public class Sheep extends MinionCard {

	public Sheep() {
		super("Sheep", 1, 1, Rarity.FREE, HeroClass.ANY, 0);
		setRace(Race.BEAST);
		
		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 427;
	}
	
	@Override
	public Minion summon() {
		return createMinion();
	}
}
