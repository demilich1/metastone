package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class RecklessRocketeer extends MinionCard {

	public RecklessRocketeer() {
		super("Reckless Rocketeer", 5, 2, Rarity.FREE, HeroClass.ANY, 6);
		setDescription("Charge");
	}

	@Override
	public int getTypeId() {
		return 190;
	}



	@Override
	public Minion summon() {
		return createMinion(GameTag.CHARGE);
	}
}
