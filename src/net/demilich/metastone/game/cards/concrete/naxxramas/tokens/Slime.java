package net.demilich.metastone.game.cards.concrete.naxxramas.tokens;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class Slime extends MinionCard {

	public Slime() {
		super("Slime", 1, 2, Rarity.COMMON, HeroClass.ANY, 1);

		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 430;
	}

	@Override
	public Minion summon() {
		return createMinion(GameTag.TAUNT);
	}
}
