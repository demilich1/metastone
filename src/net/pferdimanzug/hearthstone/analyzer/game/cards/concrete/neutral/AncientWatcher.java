package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

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
