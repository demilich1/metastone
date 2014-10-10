package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class BaineBloodhoof extends MinionCard {

	public BaineBloodhoof() {
		super("Baine Bloodhoof", 4, 5, Rarity.LEGENDARY, HeroClass.ANY, 4);

		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 432;
	}

	@Override
	public Minion summon() {
		return createMinion();
	}
}
