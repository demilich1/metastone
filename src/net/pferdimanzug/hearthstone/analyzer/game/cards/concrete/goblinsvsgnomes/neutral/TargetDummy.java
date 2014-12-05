package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;

public class TargetDummy extends MinionCard {

	public TargetDummy() {
		super("Target Dummy", 0, 2, Rarity.RARE, HeroClass.ANY, 0);
		setDescription("Taunt");
		setRace(Race.MECH);
	}

	@Override
	public Minion summon() {
		return createMinion(GameTag.TAUNT);
	}

}
