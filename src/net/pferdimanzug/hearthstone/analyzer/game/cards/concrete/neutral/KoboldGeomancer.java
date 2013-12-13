package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Minion;

public class KoboldGeomancer extends MinionCard {

	public KoboldGeomancer() {
		super("Kobold Geomancer", Rarity.FREE, HeroClass.ANY, 2);
	}

	@Override
	public Minion summon() {
		Minion koboldGeomancer = createMinion(2, 2);
		koboldGeomancer.setTag(GameTag.SPELL_POWER, 1);
		return koboldGeomancer;
	}

}
