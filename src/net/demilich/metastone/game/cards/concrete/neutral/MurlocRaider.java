package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;

public class MurlocRaider extends MinionCard {

	public MurlocRaider() {
		super("Murloc Raider", 2, 1, Rarity.FREE, HeroClass.ANY, 1);
		setRace(Race.MURLOC);
	}

	@Override
	public int getTypeId() {
		return 170;
	}

	@Override
	public Minion summon() {
		return createMinion();
	}
}
