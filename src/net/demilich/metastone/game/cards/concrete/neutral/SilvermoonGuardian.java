package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

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
