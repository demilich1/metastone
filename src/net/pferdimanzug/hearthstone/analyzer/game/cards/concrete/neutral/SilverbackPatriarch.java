package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Minion;

public class SilverbackPatriarch extends MinionCard {

	public SilverbackPatriarch() {
		super("Silverback Patriarch", Rarity.FREE, HeroClass.ANY, 3);
	}

	@Override
	public Minion summon() {
		return createMinion(1, 4, GameTag.TAUNT);
	}

}
