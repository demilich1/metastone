package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class WindfuryHarpy extends MinionCard {

	public WindfuryHarpy() {
		super("Windfury Harpy", 4, 5, Rarity.COMMON, HeroClass.ANY, 6);
		setDescription("Windfury");
	}

	@Override
	public int getTypeId() {
		return 226;
	}



	@Override
	public Minion summon() {
		return createMinion(GameTag.WINDFURY);
	}
}
