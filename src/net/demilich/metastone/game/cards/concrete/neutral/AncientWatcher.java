package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class AncientWatcher extends MinionCard {

	public AncientWatcher() {
		super("Ancient Watcher", 4, 5, Rarity.RARE, HeroClass.ANY, 2);
		setDescription("Can't Attack.");
	}

	@Override
	public int getTypeId() {
		return 85;
	}



	@Override
	public Minion summon() {
		return createMinion(GameTag.CANNOT_ATTACK);
	}
}
