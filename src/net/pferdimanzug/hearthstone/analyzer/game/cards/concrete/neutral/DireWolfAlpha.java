package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.aura.Aura;
import net.pferdimanzug.hearthstone.analyzer.game.aura.BuffAura;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class DireWolfAlpha extends MinionCard {

	public DireWolfAlpha() {
		super("Dire Wolf Alpha", 2, 2, Rarity.COMMON, HeroClass.ANY, 2);
		setDescription("Adjacent minions have +1 Attack.");
	}

	@Override
	public int getTypeId() {
		return 117;
	}
	


	@Override
	public Minion summon() {
		Minion direWolfAlpha = createMinion();
		direWolfAlpha.setRace(Race.BEAST);
		Aura direWolfAlphaAura = new BuffAura(1, 0, EntityReference.ADJACENT_MINIONS);
		direWolfAlpha.setSpellTrigger(direWolfAlphaAura);
		return direWolfAlpha;
	}
}
