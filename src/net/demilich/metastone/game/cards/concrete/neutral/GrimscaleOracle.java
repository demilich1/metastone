package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.aura.Aura;
import net.demilich.metastone.game.spells.aura.BuffAura;
import net.demilich.metastone.game.targeting.EntityReference;

public class GrimscaleOracle extends MinionCard {

	public GrimscaleOracle() {
		super("Grimscale Oracle", 1, 1, Rarity.FREE, HeroClass.ANY, 1);
		setDescription("ALL other Murlocs have +1 Attack.");
		setRace(Race.MURLOC);
	}

	@Override
	public int getTypeId() {
		return 136;
	}

	@Override
	public Minion summon() {
		Minion grimscaleOracle = createMinion();
		Aura grimscaleOracleAura = new BuffAura(1, 0, EntityReference.ALL_MINIONS, Race.MURLOC);
		grimscaleOracle.setSpellTrigger(grimscaleOracleAura);
		return grimscaleOracle;
	}
}
