package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class SilverbackPatriarch extends MinionCard {

	public SilverbackPatriarch() {
		super("Silverback Patriarch", 1, 4, Rarity.FREE, HeroClass.ANY, 3);
		setDescription("Taunt");
	}

	@Override
	public int getTypeId() {
		return 198;
	}



	@Override
	public Minion summon() {
		return createMinion(GameTag.TAUNT);
	}
}
