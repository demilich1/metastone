package net.demilich.metastone.game.cards.concrete.blackrockmountain;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;

public class FlameLionForm extends MinionCard {
	
	public FlameLionForm() {
		super("Druid of the Flame", 5, 2, Rarity.COMMON, HeroClass.DRUID, 3);
		setRace(Race.BEAST);
		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 635;
	}



	@Override
	public Minion summon() {
		return createMinion();
	}
}
