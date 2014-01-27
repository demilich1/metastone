package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;

public class StranglethornTiger extends MinionCard {

	public StranglethornTiger() {
		super("StranglethornTiger", Rarity.COMMON, HeroClass.ANY, 5);
	}

	@Override
	public Minion summon() {
		return createMinion(5, 5, Race.BEAST, GameTag.STEALTHED);
	}

}
