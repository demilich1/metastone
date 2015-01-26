package net.demilich.metastone.game.cards.concrete.tokens.warlock;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;

public class Infernal extends MinionCard {

	public Infernal() {
		super("Infernal", 6, 6, Rarity.COMMON, HeroClass.WARLOCK, 6);
		setRace(Race.DEMON);

		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 470;
	}

	@Override
	public Minion summon() {
		return createMinion();
	}
}
