package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class Sunwalker extends MinionCard {

	public Sunwalker() {
		super("Sunwalker", 4, 5, Rarity.RARE, HeroClass.ANY, 6);
		setDescription("Taunt. Divine Shield");
	}

	@Override
	public int getTypeId() {
		return 212;
	}



	@Override
	public Minion summon() {
		return createMinion(GameTag.TAUNT, GameTag.DIVINE_SHIELD);
	}
}
