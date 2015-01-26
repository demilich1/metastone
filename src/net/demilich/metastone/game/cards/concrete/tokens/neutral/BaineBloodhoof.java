package net.demilich.metastone.game.cards.concrete.tokens.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class BaineBloodhoof extends MinionCard {

	public BaineBloodhoof() {
		super("Baine Bloodhoof", 4, 5, Rarity.LEGENDARY, HeroClass.ANY, 4);

		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 432;
	}

	@Override
	public Minion summon() {
		return createMinion();
	}
}
