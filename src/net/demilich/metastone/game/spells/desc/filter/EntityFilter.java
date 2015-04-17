package net.demilich.metastone.game.spells.desc.filter;

import net.demilich.metastone.game.entities.Entity;

public abstract class EntityFilter {

	private final FilterDesc desc;

	public EntityFilter(FilterDesc desc) {
		this.desc = desc;
	}

	public boolean matches(Entity entity) {
		return this.test(entity, desc);
	}

	protected abstract boolean test(Entity entity, FilterDesc desc);

}
