package net.demilich.metastone.game.cards.concrete.tokens.rogue;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class DefiasBandit extends MinionCard {

	public DefiasBandit() {
		super("Defias Bandit", 2, 1, Rarity.FREE, HeroClass.ROGUE, 2);

		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 456;
	}

	@Override
	public Minion summon() {
		return createMinion();
	}
}
