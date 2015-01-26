package net.demilich.metastone.game.cards.concrete.naxxramas;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class BaronRivendare extends MinionCard {

	public BaronRivendare() {
		super("Baron Rivendare", 1, 7, Rarity.LEGENDARY, HeroClass.ANY, 4);
		setDescription("Your minions trigger their Deathrattles twice.");
	}

	@Override
	public int getTypeId() {
		return 413;
	}

	@Override
	public Minion summon() {
		Minion baronRivendare = createMinion(GameTag.DOUBLE_DEATHRATTLES);
		return baronRivendare;
	}
}
