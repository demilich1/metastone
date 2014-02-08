package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;

public class CoreHound extends MinionCard {

	public CoreHound() {
		super("Core Hound", 9, 5, Rarity.FREE, HeroClass.ANY, 7);
	}

	@Override
	public Minion summon() {
		return createMinion(Race.BEAST);
	}

}
