package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;

public class Puddlestomper extends MinionCard {

	public Puddlestomper() {
		super("Puddlestomper", 3, 2, Rarity.COMMON, HeroClass.ANY, 2);
		setRace(Race.MURLOC);
	}

	@Override
	public int getTypeId() {
		return 540;
	}



	@Override
	public Minion summon() {
		return createMinion();
	}
}
