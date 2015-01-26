package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;

public class BluegillWarrior extends MinionCard {

	public BluegillWarrior() {
		super("Bluegill Warrior", 2, 1, Rarity.FREE, HeroClass.ANY, 2);
		setDescription("Charge");
		setRace(Race.MURLOC);
	}

	@Override
	public int getTypeId() {
		return 99;
	}

	@Override
	public Minion summon() {
		return createMinion(GameTag.CHARGE);
	}
}
