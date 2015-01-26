package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class Shieldbearer extends MinionCard {

	public Shieldbearer() {
		super("Shieldbearer", 0, 4, Rarity.COMMON, HeroClass.ANY, 1);
		setDescription("Taunt");
	}

	@Override
	public int getTypeId() {
		return 197;
	}



	@Override
	public Minion summon() {
		return createMinion(GameTag.TAUNT);
	}
}
