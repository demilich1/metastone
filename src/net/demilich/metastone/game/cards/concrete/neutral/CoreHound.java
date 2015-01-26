package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;

public class CoreHound extends MinionCard {

	public CoreHound() {
		super("Core Hound", 9, 5, Rarity.FREE, HeroClass.ANY, 7);
		setRace(Race.BEAST);
	}

	@Override
	public int getTypeId() {
		return 108;
	}



	@Override
	public Minion summon() {
		return createMinion();
	}
}
