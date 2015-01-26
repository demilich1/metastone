package net.demilich.metastone.game.cards.concrete.tokens.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class Gnoll extends MinionCard {

	public Gnoll() {
		super("Gnoll", 2, 2, Rarity.FREE, HeroClass.ANY, 2);

		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 441;
	}

	@Override
	public Minion summon() {
		return createMinion(GameTag.TAUNT);
	}
}
