package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;

public class TargetDummy extends MinionCard {

	public TargetDummy() {
		super("Target Dummy", 0, 2, Rarity.RARE, HeroClass.ANY, 0);
		setDescription("Taunt");
		setRace(Race.MECH);
	}

	@Override
	public int getTypeId() {
		return 547;
	}



	@Override
	public Minion summon() {
		return createMinion(GameTag.TAUNT);
	}
}
