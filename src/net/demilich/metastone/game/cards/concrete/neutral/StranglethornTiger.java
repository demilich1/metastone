package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;

public class StranglethornTiger extends MinionCard {

	public StranglethornTiger() {
		super("Stranglethorn Tiger", 5, 5, Rarity.COMMON, HeroClass.ANY, 5);
		setDescription("Stealth");
		setRace(Race.BEAST);
	}

	@Override
	public int getTypeId() {
		return 210;
	}



	@Override
	public Minion summon() {
		return createMinion(GameTag.STEALTHED);
	}
}
