package net.demilich.metastone.game.cards.concrete.shaman;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class DustDevil extends MinionCard {

	public DustDevil() {
		super("Dust Devil", 3, 1, Rarity.COMMON, HeroClass.SHAMAN, 1);
		setDescription("Windfury. Overload: (2)");
		setTag(GameTag.OVERLOAD, 2);
	}

	@Override
	public int getTypeId() {
		return 314;
	}



	@Override
	public Minion summon() {
		return createMinion(GameTag.WINDFURY);
	}
}
