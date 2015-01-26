package net.demilich.metastone.game.cards.concrete.tokens.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class FlameOfAzzinoth extends MinionCard {

	public FlameOfAzzinoth() {
		super("Flame of Azzinoth", 2, 1, Rarity.FREE, HeroClass.ANY, 1);

		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 440;
	}

	@Override
	public Minion summon() {
		return createMinion();
	}
}
