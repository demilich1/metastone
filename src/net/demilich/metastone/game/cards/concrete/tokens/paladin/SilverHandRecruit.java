package net.demilich.metastone.game.cards.concrete.tokens.paladin;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.UniqueEntity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class SilverHandRecruit extends MinionCard {

	public SilverHandRecruit() {
		super("Silver Hand Recruit", 1, 1, Rarity.FREE, HeroClass.PALADIN, 1);
		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 455;
	}

	@Override
	public Minion summon() {
		Minion silverHandRecruit = createMinion();
		silverHandRecruit.setTag(GameTag.UNIQUE_ENTITY, UniqueEntity.SILVER_HAND_RECRUIT);
		return silverHandRecruit;
	}
}
