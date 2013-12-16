package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class GoldshireFootman extends MinionCard {

	public GoldshireFootman() {
		super("Goldshire Footman", Rarity.FREE, HeroClass.ANY, 1);
	}

	@Override
	public Minion summon() {
		return createMinion(1, 2, GameTag.TAUNT);
	}

}
