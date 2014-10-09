package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.paladin;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class SilverHandRecruit extends MinionCard {

	public SilverHandRecruit() {
		super("SilverHand Recruit", 1, 1, Rarity.FREE, HeroClass.PALADIN, 1);

		setCollectible(false);
	}

	@Override
	public Minion summon() {
		return createMinion();
	}

	@Override
	public int getTypeId() {
		return 455;
	}
}
