package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class WarGolem extends MinionCard {

	public WarGolem() {
		super("War Golem", 7, 7, Rarity.FREE, HeroClass.ANY, 7);
	}

	@Override
	public int getTypeId() {
		return 224;
	}



	@Override
	public Minion summon() {
		return createMinion();
	}
}
