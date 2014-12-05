package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;

public class LostTallstrider extends MinionCard {

	public LostTallstrider() {
		super("Lost Tallstrider", 5, 4, Rarity.COMMON, HeroClass.ANY, 4);
		setRace(Race.BEAST);
	}

	@Override
	public Minion summon() {
		return createMinion();
	}

}
