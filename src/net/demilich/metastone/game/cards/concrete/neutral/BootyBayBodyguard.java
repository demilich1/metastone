package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;


public class BootyBayBodyguard extends MinionCard {

	public BootyBayBodyguard() {
		super("Booty Bay Bodyguard", 5, 4, Rarity.FREE, HeroClass.ANY, 5);
		setDescription("Taunt");
	}

	@Override
	public int getTypeId() {
		return 100;
	}



	@Override
	public Minion summon() {
		return createMinion(GameTag.TAUNT);
	}
}
