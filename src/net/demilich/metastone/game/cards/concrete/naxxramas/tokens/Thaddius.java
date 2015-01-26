package net.demilich.metastone.game.cards.concrete.naxxramas.tokens;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class Thaddius extends MinionCard {

	public Thaddius() {
		super("Thaddius", 11, 11, Rarity.LEGENDARY, HeroClass.ANY, 10);

		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 410;
	}

	@Override
	public Minion summon() {
		return createMinion();
	}
}
