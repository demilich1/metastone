package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class MagmaRager extends MinionCard {

	public MagmaRager() {
		super("Magma Rager", 5, 1, Rarity.FREE, HeroClass.ANY, 3);
	}

	@Override
	public int getTypeId() {
		return 160;
	}



	@Override
	public Minion summon() {
		return createMinion();
	}
}
