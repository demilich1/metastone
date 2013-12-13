package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Minion;


public class BootyBayBodyguard extends MinionCard {

	public BootyBayBodyguard() {
		super("Booty Bay Bodyguard", Rarity.FREE, HeroClass.ANY, 5);
	}

	@Override
	public Minion summon() {
		return createMinion(5, 4, GameTag.TAUNT);
	}

}
