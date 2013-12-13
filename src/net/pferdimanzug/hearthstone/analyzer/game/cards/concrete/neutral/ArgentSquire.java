package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Minion;

public class ArgentSquire extends MinionCard {

	public ArgentSquire() {
		super("Argent Squire", Rarity.COMMON, HeroClass.ANY, 1);
	}

	@Override
	public Minion summon() {
		Minion argentSquire = createMinion(1, 1, GameTag.DIVINE_SHIELD);
		return argentSquire;
	}

}
