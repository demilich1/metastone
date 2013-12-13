package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Race;

public class BloodfenRaptor extends MinionCard {

	public BloodfenRaptor() {
		super("Bloodfen Raptor", Rarity.FREE, HeroClass.ANY, 2);
	}

	@Override
	public Minion summon() {
		Minion bloodfenRaptor = createMinion(3, 2, Race.BEAST);
		return bloodfenRaptor;
	}

}
