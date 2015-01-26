package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;

public class StonetuskBoar extends MinionCard {

	public StonetuskBoar() {
		super("Stonetusk Boar", 1, 1, Rarity.FREE, HeroClass.ANY, 1);
		setDescription("Charge");
		setRace(Race.BEAST);
	}

	@Override
	public int getTypeId() {
		return 206;
	}



	@Override
	public Minion summon() {
		return createMinion(GameTag.CHARGE);
	}
}
