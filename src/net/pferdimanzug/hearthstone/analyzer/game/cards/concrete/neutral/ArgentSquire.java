package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class ArgentSquire extends MinionCard {

	public ArgentSquire() {
		super("Argent Squire", 1, 1, Rarity.COMMON, HeroClass.ANY, 1);
		setDescription("Divine Shield");
	}

	@Override
	public int getTypeId() {
		return 90;
	}



	@Override
	public Minion summon() {
		Minion argentSquire = createMinion(GameTag.DIVINE_SHIELD);
		return argentSquire;
	}
}
