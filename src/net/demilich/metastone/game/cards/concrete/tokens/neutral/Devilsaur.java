package net.demilich.metastone.game.cards.concrete.tokens.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;

public class Devilsaur extends MinionCard {

	public Devilsaur() {
		super("Devilsaur", 5, 5, Rarity.COMMON, HeroClass.ANY, 5);
		setRace(Race.BEAST);

		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 436;
	}

	@Override
	public Minion summon() {
		return createMinion();
	}
}
