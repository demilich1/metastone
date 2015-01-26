package net.demilich.metastone.game.cards.concrete.tokens.mage;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class SpellbenderToken extends MinionCard {

	public SpellbenderToken() {
		super("Spellbender", 1, 3, Rarity.EPIC, HeroClass.MAGE, 0);

		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 428;
	}

	@Override
	public Minion summon() {
		return createMinion();
	}
}
