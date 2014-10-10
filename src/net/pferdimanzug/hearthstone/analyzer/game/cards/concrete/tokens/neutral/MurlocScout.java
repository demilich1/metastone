package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;

public class MurlocScout extends MinionCard {

	public MurlocScout() {
		super("Murloc Scout", 1, 1, Rarity.FREE, HeroClass.ANY, 0);
		setRace(Race.MURLOC);

		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 447;
	}

	@Override
	public Minion summon() {
		return createMinion();
	}
}
