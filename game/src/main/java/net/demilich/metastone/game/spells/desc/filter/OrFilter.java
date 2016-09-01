package net.demilich.metastone.game.spells.desc.filter;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;

public class OrFilter extends EntityFilter {

	public OrFilter(FilterDesc desc) {
		super(desc);
	}

	@Override
	protected boolean test(GameContext context, Player player, Entity entity) {
		EntityFilter[] filters = (EntityFilter[]) desc.get(FilterArg.FILTERS);
		for (EntityFilter filter : filters) {
			if (filter.matches(context, player, entity)) {
				return true;
			}
		}
		return false;
	}

}
