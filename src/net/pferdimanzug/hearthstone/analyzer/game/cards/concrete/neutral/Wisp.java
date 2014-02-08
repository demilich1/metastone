package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class Wisp extends MinionCard {
	
	public Wisp() {
		super("Wisp", 1, 1, Rarity.COMMON, HeroClass.ANY, 0);
	}

	@Override
	public Minion summon() {
		return createMinion();
	}

}
