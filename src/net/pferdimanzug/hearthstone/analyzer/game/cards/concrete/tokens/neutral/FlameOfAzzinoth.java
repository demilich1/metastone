package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class FlameOfAzzinoth extends MinionCard {

	public FlameOfAzzinoth() {
		super("Flame of Azzinoth", 2, 1, Rarity.FREE, HeroClass.ANY, 1);

		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 440;
	}

	@Override
	public Minion summon() {
		return createMinion();
	}
}
