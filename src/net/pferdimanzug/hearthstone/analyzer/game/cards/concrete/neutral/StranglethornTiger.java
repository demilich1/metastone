package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;

public class StranglethornTiger extends MinionCard {

	public StranglethornTiger() {
		super("Stranglethorn Tiger", 5, 5, Rarity.COMMON, HeroClass.ANY, 5);
		setDescription("Stealth");
		setTag(GameTag.RACE, Race.BEAST);
	}

	@Override
	public Minion summon() {
		return createMinion(GameTag.STEALTHED);
	}

}
