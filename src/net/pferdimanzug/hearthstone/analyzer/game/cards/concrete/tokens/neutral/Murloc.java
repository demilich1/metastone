package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;

public class Murloc extends MinionCard {

	public Murloc() {
		super("Murloc", 1, 1, Rarity.FREE, HeroClass.ANY, 1);
		setRace(Race.MURLOC);

		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 446;
	}

	@Override
	public Minion summon() {
		return createMinion();
	}
}
