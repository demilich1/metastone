package net.demilich.metastone.game.cards.concrete.tokens.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;

public class Boar extends MinionCard {

	public Boar() {
		super("Boar", 1, 1, Rarity.FREE, HeroClass.ANY, 1);
		setRace(Race.BEAST);

		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 433;
	}

	@Override
	public Minion summon() {
		return createMinion();
	}
}
