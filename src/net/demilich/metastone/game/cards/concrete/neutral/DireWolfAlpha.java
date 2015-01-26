package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.aura.Aura;
import net.demilich.metastone.game.spells.aura.BuffAura;
import net.demilich.metastone.game.targeting.EntityReference;

public class DireWolfAlpha extends MinionCard {

	public DireWolfAlpha() {
		super("Dire Wolf Alpha", 2, 2, Rarity.COMMON, HeroClass.ANY, 2);
		setDescription("Adjacent minions have +1 Attack.");
		setRace(Race.BEAST);
	}

	@Override
	public int getTypeId() {
		return 117;
	}

	@Override
	public Minion summon() {
		Minion direWolfAlpha = createMinion();
		Aura direWolfAlphaAura = new BuffAura(1, 0, EntityReference.ADJACENT_MINIONS);
		direWolfAlpha.setSpellTrigger(direWolfAlphaAura);
		return direWolfAlpha;
	}
}
