package net.demilich.metastone.game.cards.concrete.tokens.hunter;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;

public class Huffer extends MinionCard {

	public Huffer() {
		super("Huffer", 4, 2, Rarity.FREE, HeroClass.HUNTER, 3);
		setDescription("Charge");
		setRace(Race.BEAST);
		
		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 421;
	}

	@Override
	public Minion summon() {
		return createMinion(GameTag.CHARGE);
	}
}
