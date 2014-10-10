package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;

public class Imp extends MinionCard {

	public Imp() {
		super("Imp", 1, 1, Rarity.RARE, HeroClass.ANY, 1);
		setRace(Race.DEMON);

		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 443;
	}

	@Override
	public Minion summon() {
		return createMinion();
	}
}
