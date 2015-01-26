package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class Wolfrider extends MinionCard {

	public Wolfrider() {
		super("Wolfrider", 3, 1, Rarity.FREE, HeroClass.ANY, 3);
		setDescription("Charge");
	}

	@Override
	public int getTypeId() {
		return 228;
	}



	@Override
	public Minion summon() {
		return createMinion(GameTag.CHARGE);
	}
}
