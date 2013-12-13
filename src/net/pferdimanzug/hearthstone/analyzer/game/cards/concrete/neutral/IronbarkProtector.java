package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Minion;

public class IronbarkProtector extends MinionCard {

	public IronbarkProtector(String name, Rarity rarity, HeroClass classRestriction, int manaCost) {
		super("Ironbark Protector", Rarity.FREE, HeroClass.DRUID, 8);
	}

	@Override
	public Minion summon() {
		return createMinion(8, 8, GameTag.TAUNT);
	}

}
