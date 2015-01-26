package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;

public class SpiderTank extends MinionCard {

	public SpiderTank() {
		super("Spider Tank", 3, 4, Rarity.COMMON, HeroClass.ANY, 3);
		setRace(Race.MECH);
	}

	@Override
	public int getTypeId() {
		return 545;
	}



	@Override
	public Minion summon() {
		return createMinion();
	}
}
