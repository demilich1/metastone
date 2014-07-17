package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class ThrallmarFarseer extends MinionCard {

	public ThrallmarFarseer() {
		super("Thrallmar Farseer", 2, 3, Rarity.COMMON, HeroClass.ANY, 3);
		setDescription("Windfury");
	}

	@Override
	public int getTypeId() {
		return 218;
	}



	@Override
	public Minion summon() {
		return createMinion(GameTag.WINDFURY);
	}
}
