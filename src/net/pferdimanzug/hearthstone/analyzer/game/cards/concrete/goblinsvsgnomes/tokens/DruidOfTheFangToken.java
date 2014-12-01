package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.tokens;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class DruidOfTheFangToken extends MinionCard {

	public DruidOfTheFangToken() {
		super("Druid Of The Fang Token", 7, 7, Rarity.FREE, HeroClass.DRUID, 5);
		setCollectible(false);
	}

	@Override
	public Minion summon() {
		return createMinion();
	}

}
