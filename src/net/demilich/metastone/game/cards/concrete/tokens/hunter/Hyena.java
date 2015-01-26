package net.demilich.metastone.game.cards.concrete.tokens.hunter;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;

public class Hyena extends MinionCard {

	public Hyena() {
		super("Hyena", 2, 2, Rarity.RARE, HeroClass.HUNTER, 2);
		setRace(Race.BEAST);
		
		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 422;
	}
	
	@Override
	public Minion summon() {
		return createMinion();
	}
}
