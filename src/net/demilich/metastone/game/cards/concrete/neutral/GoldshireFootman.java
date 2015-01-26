package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class GoldshireFootman extends MinionCard {

	public GoldshireFootman() {
		super("Goldshire Footman", 1, 2, Rarity.FREE, HeroClass.ANY, 1);
		setDescription("Taunt");
	}

	@Override
	public int getTypeId() {
		return 135;
	}



	@Override
	public Minion summon() {
		return createMinion(GameTag.TAUNT);
	}
}
