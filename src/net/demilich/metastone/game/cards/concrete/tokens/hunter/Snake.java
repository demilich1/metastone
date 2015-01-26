package net.demilich.metastone.game.cards.concrete.tokens.hunter;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;

public class Snake extends MinionCard {

	public Snake() {
		super("Snake", 1, 1, Rarity.COMMON, HeroClass.HUNTER, 0);
		setRace(Race.BEAST);
		
		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 425;
	}
	
	@Override
	public Minion summon() {
		return createMinion();
	}
}
