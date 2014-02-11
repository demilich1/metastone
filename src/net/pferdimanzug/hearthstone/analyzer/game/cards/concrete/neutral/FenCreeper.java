package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class FenCreeper extends MinionCard {

	public FenCreeper() {
		super("Fen Creeper", 3, 6, Rarity.COMMON, HeroClass.ANY, 5);
		setDescription("Taunt");
	}

	@Override
	public Minion summon() {
		return createMinion(GameTag.TAUNT);
	}

}
