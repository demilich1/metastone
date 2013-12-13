package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Minion;

public class SenjinShieldmasta extends MinionCard {

	public SenjinShieldmasta() {
		super("Sen'jin Shieldmasta", Rarity.FREE, HeroClass.ANY, 4);
	}

	@Override
	public Minion summon() {
		return createMinion(3, 5, GameTag.TAUNT);
	}

}
