package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.naxxramas.tokens;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

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
