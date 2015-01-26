package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class FaerieDragon extends MinionCard {

	public FaerieDragon() {
		super("Faerie Dragon", 3, 2, Rarity.COMMON, HeroClass.ANY, 2);
		setDescription("Can't be targeted by Spells or Hero Powers.");
	}

	@Override
	public int getTypeId() {
		return 126;
	}



	@Override
	public Minion summon() {
		return createMinion(GameTag.UNTARGETABLE_BY_SPELLS);
	}
}
