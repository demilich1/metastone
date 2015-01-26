package net.demilich.metastone.game.cards.concrete.shaman;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class EarthElemental extends MinionCard {

	public EarthElemental() {
		super("Earth Elemental", 7, 8, Rarity.EPIC, HeroClass.SHAMAN, 5);
		setDescription("Taunt. Overload: (3)");
		setTag(GameTag.OVERLOAD, 3);
	}

	@Override
	public int getTypeId() {
		return 315;
	}



	@Override
	public Minion summon() {
		return createMinion(GameTag.TAUNT);
	}
}
