package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.tokens;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;

public class CobraForm extends MinionCard {

	public CobraForm() {
		super("Druid Of The Fang (Cobra Form)", 7, 7, Rarity.FREE, HeroClass.DRUID, 5);
		setRace(Race.BEAST);
		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 592;
	}



	@Override
	public Minion summon() {
		return createMinion();
	}
}
