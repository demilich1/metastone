package net.demilich.metastone.game.spells.desc.filter;

import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.EntityType;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;

public class RaceFilter extends EntityFilter {

	
	public RaceFilter(FilterDesc desc) {
		super(desc);
	}

	@Override
	protected boolean test(Entity entity, FilterDesc desc) {
		if (entity.getEntityType() != EntityType.MINION) {
			return false;
		}
		Race race = (Race) desc.get(FilterArg.RACE);
		Minion minion = (Minion) entity;
		return minion.getRace() == race;
	}

}
