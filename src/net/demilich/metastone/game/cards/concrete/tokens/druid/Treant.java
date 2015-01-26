package net.demilich.metastone.game.cards.concrete.tokens.druid;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class Treant extends MinionCard {

	private final GameTag[] treantTags;

	public Treant(GameTag... tags) {
		super("Treant", 2, 2, Rarity.FREE, HeroClass.DRUID, 1);
		treantTags = tags;
		
		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 419;
	}

	@Override
	public Minion summon() {
		return createMinion(treantTags);
	}
}
