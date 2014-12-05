package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;

public class FlyingMachine extends MinionCard {

	public FlyingMachine() {
		super("Flying Machine", 1, 4, Rarity.COMMON, HeroClass.ANY, 3);
		setDescription("Windfury");
		setRace(Race.MECH);
	}

	@Override
	public Minion summon() {
		Minion flyingMachine = createMinion(GameTag.WINDFURY);
		return flyingMachine;
	}

}
