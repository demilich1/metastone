package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.tokens;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;

public class CobraForm extends MinionCard {

	public CobraForm() {
		super("Druid Of The Fang (Cobra Form)", 7, 7, Rarity.FREE, HeroClass.DRUID, 5);
		setRace(Race.BEAST);
		setCollectible(false);
	}

	@Override
	public Minion summon() {
		return createMinion();
	}

}
