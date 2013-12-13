package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Minion;

public class ArgentCommander extends MinionCard {

	public ArgentCommander() {
		super("Argent Commander", Rarity.RARE, HeroClass.ANY, 6);
	}

	@Override
	public Minion summon() {
		Minion argentCommander = createMinion(4, 3, GameTag.CHARGE, GameTag.DIVINE_SHIELD);
		return argentCommander;
	}

}
