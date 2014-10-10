package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.druid;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

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
