package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.naxxramas;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

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
