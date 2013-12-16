package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class RecklessRocketeer extends MinionCard {

	public RecklessRocketeer() {
		super("Reckless Rocketeer", Rarity.FREE, HeroClass.ANY, 6);
	}

	@Override
	public Minion summon() {
		return createMinion(5, 2, GameTag.CHARGE);
	}

}
