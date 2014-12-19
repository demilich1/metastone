package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.priest;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class ShadowOfNothing extends MinionCard {

	public ShadowOfNothing() {
		super("Shadow of Nothing", 0, 1, Rarity.EPIC, HeroClass.PRIEST, 0);
		setDescription("Mindgames whiffed! Your opponent had no minions!");
		setCollectible(false);
	}

	@Override
	public Minion summon() {
		return createMinion();
	}
	
}