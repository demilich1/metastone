package net.demilich.metastone.game.cards.concrete.tokens.mage;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;

public class Sheep extends MinionCard {

	public Sheep() {
		super("Sheep", 1, 1, Rarity.FREE, HeroClass.ANY, 0);
		setRace(Race.BEAST);
		
		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 427;
	}
	
	@Override
	public Minion summon() {
		return createMinion();
	}
}
