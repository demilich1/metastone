package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

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
