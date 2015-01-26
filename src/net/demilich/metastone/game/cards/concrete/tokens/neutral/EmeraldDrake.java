package net.demilich.metastone.game.cards.concrete.tokens.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;

public class EmeraldDrake extends MinionCard {

	public EmeraldDrake() {
		super("Emerald Drake", 7, 6, Rarity.FREE, HeroClass.ANY, 4);
		setRace(Race.DRAGON);

		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 438;
	}

	@Override
	public Minion summon() {
		return createMinion();
	}
}
