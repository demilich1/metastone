package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;

public class IronfurGrizzly extends MinionCard {

	public IronfurGrizzly() {
		super("Ironfur Grizzly", 3, 3, Rarity.FREE, HeroClass.ANY, 3);
		setDescription("Taunt");
		setRace(Race.BEAST);
	}

	@Override
	public int getTypeId() {
		return 148;
	}



	@Override
	public Minion summon() {
		return createMinion(GameTag.TAUNT);
	}
}
