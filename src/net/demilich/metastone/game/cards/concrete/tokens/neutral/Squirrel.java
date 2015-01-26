package net.demilich.metastone.game.cards.concrete.tokens.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;

public class Squirrel extends MinionCard {

	public Squirrel() {
		super("Squirrel", 1, 1, Rarity.COMMON, HeroClass.ANY, 1);
		setRace(Race.BEAST);

		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 451;
	}

	@Override
	public Minion summon() {
		return createMinion();
	}
}
