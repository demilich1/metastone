package net.demilich.metastone.game.cards.concrete.tokens.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;

public class Imp extends MinionCard {

	public Imp() {
		super("Imp", 1, 1, Rarity.RARE, HeroClass.ANY, 1);
		setRace(Race.DEMON);

		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 443;
	}

	@Override
	public Minion summon() {
		return createMinion();
	}
}
