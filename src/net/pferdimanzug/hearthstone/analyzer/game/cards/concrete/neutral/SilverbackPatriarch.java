package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class SilverbackPatriarch extends MinionCard {

	public SilverbackPatriarch() {
		super("Silverback Patriarch", 1, 4, Rarity.FREE, HeroClass.ANY, 3);
		setDescription("Taunt");
	}

	@Override
	public int getTypeId() {
		return 198;
	}



	@Override
	public Minion summon() {
		return createMinion(GameTag.TAUNT);
	}
}
