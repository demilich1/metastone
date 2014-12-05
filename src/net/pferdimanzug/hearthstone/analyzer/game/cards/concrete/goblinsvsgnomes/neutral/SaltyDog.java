package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;

public class SaltyDog extends MinionCard {

	public SaltyDog() {
		super("Salty Dog", 7, 4, Rarity.COMMON, HeroClass.ANY, 5);
		setRace(Race.PIRATE);
	}

	@Override
	public int getTypeId() {
		return 542;
	}



	@Override
	public Minion summon() {
		return createMinion();
	}
}
