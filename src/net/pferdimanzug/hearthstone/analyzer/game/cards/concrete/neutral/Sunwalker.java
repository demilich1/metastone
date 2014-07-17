package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class Sunwalker extends MinionCard {

	public Sunwalker() {
		super("Sunwalker", 4, 5, Rarity.RARE, HeroClass.ANY, 6);
		setDescription("Taunt. Divine Shield");
	}

	@Override
	public int getTypeId() {
		return 212;
	}



	@Override
	public Minion summon() {
		return createMinion(GameTag.TAUNT, GameTag.DIVINE_SHIELD);
	}
}
