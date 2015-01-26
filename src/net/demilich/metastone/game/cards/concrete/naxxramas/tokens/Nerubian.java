package net.demilich.metastone.game.cards.concrete.naxxramas.tokens;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

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
