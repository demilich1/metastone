package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class FaerieDragon extends MinionCard {

	public FaerieDragon() {
		super("Faerie Dragon", Rarity.COMMON, HeroClass.ANY, 2);
	}

	@Override
	public Minion summon() {
		return createMinion(3, 2, GameTag.UNTARGETABLE_BY_SPELLS);
	}

}
