package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;

public class OasisSnapjaw extends MinionCard {

	public OasisSnapjaw() {
		super("Oasis Snapjaw", 2, 7, Rarity.FREE, HeroClass.ANY, 4);
		setRace(Race.BEAST);
	}

	@Override
	public int getTypeId() {
		return 178;
	}



	@Override
	public Minion summon() {
		return createMinion();
	}
}
