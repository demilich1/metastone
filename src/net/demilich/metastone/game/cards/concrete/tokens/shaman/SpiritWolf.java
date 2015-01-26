package net.demilich.metastone.game.cards.concrete.tokens.shaman;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class SpiritWolf extends MinionCard {

	public SpiritWolf() {
		super("Spirit Wolf", 2, 3, Rarity.RARE, HeroClass.SHAMAN, 2);
		setDescription("Taunt");

		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 459;
	}

	@Override
	public Minion summon() {
		return createMinion(GameTag.TAUNT);
	}
}
