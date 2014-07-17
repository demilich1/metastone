package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class ChillwindYeti extends MinionCard {

	public ChillwindYeti() {
		super("Chillwind Yeti", 4, 5, Rarity.FREE, HeroClass.ANY, 4);
	}

	@Override
	public int getTypeId() {
		return 105;
	}




	@Override
	public Minion summon() {
		return createMinion();
	}
}
