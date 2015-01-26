package net.demilich.metastone.game.cards.concrete.tokens.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class VioletApprentice extends MinionCard {

	public VioletApprentice() {
		super("Violet Apprentice", 1, 1, Rarity.FREE, HeroClass.ANY, 0);

		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 452;
	}

	@Override
	public Minion summon() {
		return createMinion();
	}
}
