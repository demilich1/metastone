package net.demilich.metastone.game.cards.concrete.tokens.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class Squire extends MinionCard {

	public Squire() {
		super("Squire", 2, 2, Rarity.COMMON, HeroClass.ANY, 1);

		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 450;
	}

	@Override
	public Minion summon() {
		return createMinion();
	}
}
