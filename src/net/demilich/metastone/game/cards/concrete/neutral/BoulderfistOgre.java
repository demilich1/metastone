package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class BoulderfistOgre extends MinionCard {

	public BoulderfistOgre() {
		super("Boulderfist Ogre", 6, 7, Rarity.FREE, HeroClass.ANY, 6);
	}

	@Override
	public int getTypeId() {
		return 101;
	}



	@Override
	public Minion summon() {
		return createMinion();
	}
}
