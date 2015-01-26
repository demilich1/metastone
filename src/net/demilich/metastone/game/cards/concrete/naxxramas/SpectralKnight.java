package net.demilich.metastone.game.cards.concrete.naxxramas;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class SpectralKnight extends MinionCard {

	public SpectralKnight() {
		super("Spectral Knight", 4, 6, Rarity.COMMON, HeroClass.ANY, 5);
		setDescription("Can't be targeted by spells or Hero Powers.");
	}

	@Override
	public int getTypeId() {
		return 399;
	}



	@Override
	public Minion summon() {
		return createMinion(GameTag.UNTARGETABLE_BY_SPELLS);
	}
}
