package net.demilich.metastone.game.spells.desc.filter;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;

public class AndFilter extends EntityFilter {

	public AndFilter(FilterDesc desc) {
		super(desc);
	}

	@Override
	protected boolean test(GameContext context, Player player, Entity entity) {
		EntityFilter[] filters = (EntityFilter[]) desc.get(FilterArg.FILTERS);
		boolean check = true;
		for (EntityFilter filter : filters) {
			check &= filter.test(context, player, entity);
		}
		return check;
	}

}
