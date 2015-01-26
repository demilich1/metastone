package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;

public class FlyingMachine extends MinionCard {

	public FlyingMachine() {
		super("Flying Machine", 1, 4, Rarity.COMMON, HeroClass.ANY, 3);
		setDescription("Windfury");
		setRace(Race.MECH);
	}

	@Override
	public int getTypeId() {
		return 513;
	}



	@Override
	public Minion summon() {
		Minion flyingMachine = createMinion(GameTag.WINDFURY);
		return flyingMachine;
	}
}
