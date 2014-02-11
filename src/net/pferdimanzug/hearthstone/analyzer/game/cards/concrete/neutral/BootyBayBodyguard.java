package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;


public class BootyBayBodyguard extends MinionCard {

	public BootyBayBodyguard() {
		super("Booty Bay Bodyguard", 5, 4, Rarity.FREE, HeroClass.ANY, 5);
		setDescription("Taunt");
	}

	@Override
	public Minion summon() {
		return createMinion(GameTag.TAUNT);
	}

}
