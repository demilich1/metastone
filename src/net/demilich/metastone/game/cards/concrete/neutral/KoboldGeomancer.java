package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

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
