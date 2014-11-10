package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.naxxramas.tokens;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

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
