package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class ScarletCrusader extends MinionCard {

	public ScarletCrusader() {
		super("Scarlet Crusader", Rarity.COMMON, HeroClass.ANY, 3);
	}

	@Override
	public Minion summon() {
		return createMinion(3, 1, GameTag.DIVINE_SHIELD);
	}

}
