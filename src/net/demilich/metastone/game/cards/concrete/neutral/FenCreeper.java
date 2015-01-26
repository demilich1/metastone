package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class FenCreeper extends MinionCard {

	public FenCreeper() {
		super("Fen Creeper", 3, 6, Rarity.COMMON, HeroClass.ANY, 5);
		setDescription("Taunt");
	}

	@Override
	public int getTypeId() {
		return 127;
	}



	@Override
	public Minion summon() {
		return createMinion(GameTag.TAUNT);
	}
}
