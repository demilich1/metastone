package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class DamagedGolem extends MinionCard {

	public DamagedGolem() {
		super("Damaged Golem", 2, 1, Rarity.COMMON, HeroClass.ANY, 1);

		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 435;
	}

	@Override
	public Minion summon() {
		return createMinion();
	}
}
