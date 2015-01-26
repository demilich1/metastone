package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;

public class LostTallstrider extends MinionCard {

	public LostTallstrider() {
		super("Lost Tallstrider", 5, 4, Rarity.COMMON, HeroClass.ANY, 4);
		setRace(Race.BEAST);
	}

	@Override
	public int getTypeId() {
		return 528;
	}



	@Override
	public Minion summon() {
		return createMinion();
	}
}
