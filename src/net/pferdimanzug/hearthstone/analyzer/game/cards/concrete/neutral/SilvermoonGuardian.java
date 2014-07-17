package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class SilvermoonGuardian extends MinionCard {

	public SilvermoonGuardian() {
		super("Silvermoon Guardian", 3, 3, Rarity.COMMON, HeroClass.ANY, 4);
		setDescription("Divine Shield");
	}

	@Override
	public int getTypeId() {
		return 200;
	}



	@Override
	public Minion summon() {
		return createMinion(GameTag.DIVINE_SHIELD);
	}
}
