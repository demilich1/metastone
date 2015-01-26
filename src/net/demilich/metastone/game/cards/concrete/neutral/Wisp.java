package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class Wisp extends MinionCard {
	
	public Wisp() {
		super("Wisp", 1, 1, Rarity.COMMON, HeroClass.ANY, 0);
	}

	@Override
	public int getTypeId() {
		return 227;
	}



	@Override
	public Minion summon() {
		return createMinion();
	}
}
