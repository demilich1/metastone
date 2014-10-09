package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.mage;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class SpellbenderMinion extends MinionCard {

	public SpellbenderMinion() {
		super("Spellbender", 1, 3, Rarity.EPIC, HeroClass.MAGE, 0);

		setCollectible(false);
	}

	@Override
	public Minion summon() {
		return createMinion();
	}

	@Override
	public int getTypeId() {
		return 428;
	}
}
