package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class GoldshireFootman extends MinionCard {

	public GoldshireFootman() {
		super("Goldshire Footman", 1, 2, Rarity.FREE, HeroClass.ANY, 1);
		setDescription("Taunt");
	}

	@Override
	public Minion summon() {
		return createMinion(GameTag.TAUNT);
	}

}
