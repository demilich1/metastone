package net.demilich.metastone.game.cards.concrete.tokens.druid;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;

public class Panther extends MinionCard {
	public Panther() {
		super("Panther", 3, 2, Rarity.COMMON, HeroClass.DRUID, 2);
		setRace(Race.BEAST);
		
		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 418;
	}

	@Override
	public Minion summon() {
		return createMinion();
	}
}
