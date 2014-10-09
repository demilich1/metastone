package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.druid;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;

public class Panther extends MinionCard {
	public Panther() {
		super("Panther", 3, 2, Rarity.COMMON, HeroClass.DRUID, 2);
		setRace(Race.BEAST);
		
		setCollectible(false);
	}

	@Override
	public Minion summon() {
		return createMinion();
	}

	@Override
	public int getTypeId() {
		return 418;
	}
}
