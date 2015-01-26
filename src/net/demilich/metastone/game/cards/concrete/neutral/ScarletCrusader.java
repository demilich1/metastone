package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

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
