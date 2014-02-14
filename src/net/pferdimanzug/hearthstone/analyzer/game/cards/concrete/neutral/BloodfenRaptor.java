package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;

public class BloodfenRaptor extends MinionCard {

	public BloodfenRaptor() {
		super("Bloodfen Raptor", 3, 2, Rarity.FREE, HeroClass.ANY, 2);
		setTag(GameTag.RACE, Race.BEAST);
	}

	@Override
	public Minion summon() {
		Minion bloodfenRaptor = createMinion();
		return bloodfenRaptor;
	}

}
