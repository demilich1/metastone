package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;

public class OasisSnapjaw extends MinionCard {

	public OasisSnapjaw() {
		super("Oasis Snapjaw", 2, 7, Rarity.FREE, HeroClass.ANY, 4);
		setTag(GameTag.RACE, Race.BEAST);
	}

	@Override
	public Minion summon() {
		return createMinion();
	}

}
