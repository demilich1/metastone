package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class WarGolem extends MinionCard {

	public WarGolem() {
		super("War Golem", 7, 7, Rarity.FREE, HeroClass.ANY, 7);
	}

	@Override
	public int getTypeId() {
		return 224;
	}



	@Override
	public Minion summon() {
		return createMinion();
	}
}
