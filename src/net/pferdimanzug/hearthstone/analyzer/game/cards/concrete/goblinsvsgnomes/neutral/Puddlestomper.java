package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;

public class Puddlestomper extends MinionCard {

	public Puddlestomper() {
		super("Puddlestomper", 3, 2, Rarity.COMMON, HeroClass.ANY, 2);
		setRace(Race.MURLOC);
	}

	@Override
	public int getTypeId() {
		return 540;
	}



	@Override
	public Minion summon() {
		return createMinion();
	}
}
