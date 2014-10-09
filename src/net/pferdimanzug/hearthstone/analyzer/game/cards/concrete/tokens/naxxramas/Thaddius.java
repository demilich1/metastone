package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.naxxramas;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

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
