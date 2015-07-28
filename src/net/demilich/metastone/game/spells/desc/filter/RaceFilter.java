package net.demilich.metastone.game.spells.desc.filter;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Race;

public class RaceFilter extends EntityFilter {
	
	public RaceFilter(FilterDesc desc) {
		super(desc);
	}

	@Override
	protected boolean test(Entity entity, FilterDesc desc) {
		Race race = (Race) desc.get(FilterArg.RACE);
		return entity.getAttribute(Attribute.RACE) == race;
	}

}
