package net.demilich.metastone.game.cards.concrete.warlock;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;

public class Voidwalker extends MinionCard {

	public Voidwalker() {
		super("Voidwalker", 1, 3, Rarity.FREE, HeroClass.WARLOCK, 1);
		setDescription("Taunt");
		setRace(Race.DEMON);
	}

	@Override
	public int getTypeId() {
		return 358;
	}



	@Override
	public Minion summon() {
		return createMinion(GameTag.TAUNT);
	}
}
