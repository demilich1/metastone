package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;

public class OasisSnapjaw extends MinionCard {

	public OasisSnapjaw() {
		super("Oasis Snapjaw", Rarity.FREE, HeroClass.ANY, 4);
	}

	@Override
	public Minion summon() {
		return createMinion(2, 7, Race.BEAST);
	}

}
