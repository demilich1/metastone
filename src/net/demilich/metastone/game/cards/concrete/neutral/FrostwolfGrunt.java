package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class FrostwolfGrunt extends MinionCard {

	public FrostwolfGrunt() {
		super("Frostwolf Grunt", 2, 2, Rarity.FREE, HeroClass.ANY, 2);
		setDescription("Taunt");
	}

	@Override
	public int getTypeId() {
		return 130;
	}

	@Override
	public Minion summon() {
		return createMinion(GameTag.TAUNT);
	}
}
