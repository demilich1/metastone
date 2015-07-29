package net.demilich.metastone.game.spells.desc.filter;

import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;

public class DamagedFilter extends EntityFilter {

	public DamagedFilter(FilterDesc desc) {
		super(desc);
	}

	@Override
	protected boolean test(Entity entity, FilterDesc desc) {
		if (entity instanceof Actor) {
			return ((Actor) entity).isWounded();
		}
		return false;
	}

}
