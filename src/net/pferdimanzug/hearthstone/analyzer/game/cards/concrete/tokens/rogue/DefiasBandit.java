package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.rogue;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

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
