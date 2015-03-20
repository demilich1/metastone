package net.demilich.metastone.game.cards.concrete.tokens.shaman;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;

public class Frog extends MinionCard {

	public Frog() {
		super("Frog", 0, 1, Rarity.FREE, HeroClass.ANY, 0);
		setRace(Race.BEAST);
		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 624;
	}



	@Override
	public Minion summon() {
		return createMinion(GameTag.TAUNT);
	}
}
