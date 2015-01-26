package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class ChillwindYeti extends MinionCard {

	public ChillwindYeti() {
		super("Chillwind Yeti", 4, 5, Rarity.FREE, HeroClass.ANY, 4);
	}

	@Override
	public int getTypeId() {
		return 105;
	}




	@Override
	public Minion summon() {
		return createMinion();
	}
}
