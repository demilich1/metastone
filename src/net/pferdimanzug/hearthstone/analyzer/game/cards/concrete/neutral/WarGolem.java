package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Minion;

public class WarGolem extends MinionCard {

	public WarGolem() {
		super("War Golem", Rarity.FREE, HeroClass.ANY, 7);
	}

	@Override
	public Minion summon() {
		return createMinion(7, 7);
	}

}
