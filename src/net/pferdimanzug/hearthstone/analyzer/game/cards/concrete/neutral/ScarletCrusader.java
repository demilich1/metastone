package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class ScarletCrusader extends MinionCard {

	public ScarletCrusader() {
		super("Scarlet Crusader", 3, 1, Rarity.COMMON, HeroClass.ANY, 3);
		setDescription("Divine Shield");
	}

	@Override
	public int getTypeId() {
		return 192;
	}



	@Override
	public Minion summon() {
		return createMinion(GameTag.DIVINE_SHIELD);
	}
}
