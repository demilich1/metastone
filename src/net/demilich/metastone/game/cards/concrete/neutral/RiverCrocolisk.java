package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;

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
