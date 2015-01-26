package net.demilich.metastone.game.cards.concrete.tokens.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class FinkleEinhorn extends MinionCard {

	public FinkleEinhorn() {
		super("Finkle Einhorn", 3, 3, Rarity.LEGENDARY, HeroClass.ANY, 2);

		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 439;
	}

	@Override
	public Minion summon() {
		return createMinion();
	}
}
