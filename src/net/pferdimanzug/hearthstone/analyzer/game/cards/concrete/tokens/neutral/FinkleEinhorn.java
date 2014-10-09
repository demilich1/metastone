package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class FinkleEinhorn extends MinionCard {

	public FinkleEinhorn() {
		super("Finkle Einhorn", 3, 3, Rarity.LEGENDARY, HeroClass.ANY, 2);
		setCollectible(false);
	}

	@Override
	public Minion summon() {
		return createMinion();
	}

}