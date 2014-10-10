package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;

public class Devilsaur extends MinionCard {

	public Devilsaur() {
		super("Devilsaur", 5, 5, Rarity.COMMON, HeroClass.ANY, 5);
		setRace(Race.BEAST);

		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 436;
	}

	@Override
	public Minion summon() {
		return createMinion();
	}
}
