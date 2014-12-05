package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.paladin;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.UniqueEntity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

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
