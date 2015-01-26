package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;

public class YoungDragonhawk extends MinionCard {

	public YoungDragonhawk() {
		super("Young Dragonhawk", 1, 1, Rarity.COMMON, HeroClass.ANY, 1);
		setDescription("Windfury");
		setRace(Race.BEAST);
	}

	@Override
	public int getTypeId() {
		return 230;
	}



	@Override
	public Minion summon() {
		return createMinion(GameTag.WINDFURY);
	}
}
