package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;

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
