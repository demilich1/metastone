package net.demilich.metastone.game.cards.concrete.tokens.priest;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class ShadowOfNothing extends MinionCard {

	public ShadowOfNothing() {
		super("Shadow of Nothing", 0, 1, Rarity.EPIC, HeroClass.PRIEST, 0);
		setDescription("Mindgames whiffed! Your opponent had no minions!");
		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 612;
	}
	


	@Override
	public Minion summon() {
		return createMinion();
	}
}
