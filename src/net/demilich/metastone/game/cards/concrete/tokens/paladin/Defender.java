package net.demilich.metastone.game.cards.concrete.tokens.paladin;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class Defender extends MinionCard {

	public Defender() {
		super("Defender", 2, 1, Rarity.COMMON, HeroClass.PALADIN, 1);

		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 454;
	}

	@Override
	public Minion summon() {
		return createMinion();
	}
}
