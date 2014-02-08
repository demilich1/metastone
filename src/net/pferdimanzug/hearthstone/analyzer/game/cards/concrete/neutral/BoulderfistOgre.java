package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class BoulderfistOgre extends MinionCard {

	public BoulderfistOgre() {
		super("Boulderfist Ogre", 6, 7, Rarity.FREE, HeroClass.ANY, 6);
	}

	@Override
	public Minion summon() {
		return createMinion();
	}

}
