package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;

public class YoungDragonhawk extends MinionCard {

	public YoungDragonhawk() {
		super("Young Dragonhawk", 1, 1, Rarity.COMMON, HeroClass.ANY, 1);
		setDescription("Windfury");
	}

	@Override
	public Minion summon() {
		return createMinion(Race.BEAST, GameTag.WINDFURY);
	}

}
