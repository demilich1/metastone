package net.demilich.metastone.game.cards.concrete.blackrockmountain;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;

public class Whelp extends MinionCard {
	
	public Whelp() {
		super("Whelp", 2, 1, Rarity.FREE, HeroClass.ANY, 1);
		setRace(Race.DRAGON);

		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 623;
	}



	@Override
	public Minion summon() {
		return createMinion();
	}
}
