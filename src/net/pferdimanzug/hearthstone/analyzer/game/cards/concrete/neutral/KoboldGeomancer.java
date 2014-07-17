package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class KoboldGeomancer extends MinionCard {

	public KoboldGeomancer() {
		super("Kobold Geomancer", 2, 2, Rarity.FREE, HeroClass.ANY, 2);
		setDescription("Spell Damage +1");
	}

	@Override
	public int getTypeId() {
		return 152;
	}



	@Override
	public Minion summon() {
		Minion koboldGeomancer = createMinion();
		koboldGeomancer.setTag(GameTag.SPELL_POWER, 1);
		return koboldGeomancer;
	}
}
