package net.demilich.metastone.game.spells.desc.filter;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Race;

public class RaceFilter extends EntityFilter {
	
	public RaceFilter(FilterDesc desc) {
		super(desc);
	}

	@Override
	protected boolean test(Entity entity, FilterDesc desc) {
		Race race = (Race) desc.get(FilterArg.RACE);
		return entity.getTag(GameTag.RACE) == race;
	}

}
