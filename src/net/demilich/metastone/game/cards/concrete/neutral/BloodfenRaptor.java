package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;

public class BloodfenRaptor extends MinionCard {

	public BloodfenRaptor() {
		super("Bloodfen Raptor", 3, 2, Rarity.FREE, HeroClass.ANY, 2);
		setRace(Race.BEAST);
	}

	@Override
	public int getTypeId() {
		return 94;
	}



	@Override
	public Minion summon() {
		Minion bloodfenRaptor = createMinion();
		return bloodfenRaptor;
	}
}
