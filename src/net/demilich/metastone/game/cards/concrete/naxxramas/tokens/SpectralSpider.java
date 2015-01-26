package net.demilich.metastone.game.cards.concrete.naxxramas.tokens;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class SpectralSpider extends MinionCard {

	public SpectralSpider() {
		super("Spectral Spider", 1, 1, Rarity.COMMON, HeroClass.ANY, 1);

		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 431;
	}

	@Override
	public Minion summon() {
		return createMinion();
	}
}
