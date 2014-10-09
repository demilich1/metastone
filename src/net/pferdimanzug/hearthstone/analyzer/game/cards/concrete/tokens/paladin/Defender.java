package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.paladin;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class Defender extends MinionCard {

	public Defender() {
		super("Defender", 2, 1, Rarity.COMMON, HeroClass.PALADIN, 1);
		setCollectible(false);
	}

	@Override
	public Minion summon() {
		return createMinion();
	}
	
}