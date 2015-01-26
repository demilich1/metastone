package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class WindfuryHarpy extends MinionCard {

	public WindfuryHarpy() {
		super("Windfury Harpy", 4, 5, Rarity.COMMON, HeroClass.ANY, 6);
		setDescription("Windfury");
	}

	@Override
	public int getTypeId() {
		return 226;
	}

	@Override
	public Minion summon() {
		return createMinion(GameTag.WINDFURY);
	}
}
