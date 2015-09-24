package net.demilich.metastone.game.spells.desc.filter;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;

public abstract class EntityFilter {

	protected final FilterDesc desc;

	public EntityFilter(FilterDesc desc) {
		this.desc = desc;
	}

	public boolean matches(GameContext context, Player player, Entity entity) {
		boolean invert = desc.getBool(FilterArg.INVERT);
		return this.test(context, player, entity) != invert;
	}

	protected abstract boolean test(GameContext context, Player player, Entity entity);

}
