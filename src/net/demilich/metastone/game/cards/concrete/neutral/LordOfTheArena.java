package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class LordOfTheArena extends MinionCard {

	public LordOfTheArena() {
		super("Lord of the Arena", 6, 5, Rarity.FREE, HeroClass.ANY, 6);
		setDescription("Taunt");
	}

	@Override
	public int getTypeId() {
		return 157;
	}



	@Override
	public Minion summon() {
		return createMinion(GameTag.TAUNT);
	}
}
