package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;

public class RiverCrocolisk extends MinionCard {

	public RiverCrocolisk() {
		super("River Crocolisk", 2, 3, Rarity.FREE, HeroClass.ANY, 2);
		setRace(Race.BEAST);
	}

	@Override
	public int getTypeId() {
		return 191;
	}



	@Override
	public Minion summon() {
		return createMinion();
	}
}
