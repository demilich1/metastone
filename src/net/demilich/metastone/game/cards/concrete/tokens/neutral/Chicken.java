package net.demilich.metastone.game.cards.concrete.tokens.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;

public class Chicken extends MinionCard {

	public Chicken() {
		super("Chicken", 1, 1, Rarity.FREE, HeroClass.ANY, 0);
		setDescription("Hey Chicken!");
		setRace(Race.BEAST);

		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 434;
	}

	@Override
	public Minion summon() {
		return createMinion();
	}
}
