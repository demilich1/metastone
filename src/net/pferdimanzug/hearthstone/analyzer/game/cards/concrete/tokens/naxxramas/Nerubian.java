package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.naxxramas;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class Nerubian extends MinionCard {

	public Nerubian() {
		super("Nerubian", 4, 4, Rarity.FREE, HeroClass.ANY, 2);

		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 429;
	}

	@Override
	public Minion summon() {
		return createMinion();
	}
}
