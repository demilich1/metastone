package net.demilich.metastone.game.cards.concrete.tokens.hunter;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;

public class Misha extends MinionCard {

	public Misha() {
		super("Misha", 4, 4, Rarity.FREE, HeroClass.HUNTER, 3);
		setRace(Race.BEAST);
		setDescription("Taunt");
		
		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 424;
	}
	
	@Override
	public Minion summon() {
		return createMinion(GameTag.TAUNT);
	}
}
