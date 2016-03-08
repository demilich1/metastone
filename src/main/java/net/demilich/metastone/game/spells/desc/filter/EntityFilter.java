package net.demilich.metastone.game.spells.desc.filter;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;

public abstract class EntityFilter {

	protected final FilterDesc desc;

	public EntityFilter(FilterDesc desc) {
		this.desc = desc;
	}
	
	public void addArg(FilterArg arg, Object object) {
		if (desc.contains(arg)) {
			desc.remove(arg);
		}
		desc.add(arg, object);
	}

	public Object getArg(FilterArg arg) {
		return desc.get(arg);
	}
	
	public boolean hasArg(FilterArg arg) {
		return desc.contains(arg);
	}

	public boolean matches(GameContext context, Player player, Entity entity) {
		boolean invert = desc.getBool(FilterArg.INVERT);
		return this.test(context, player, entity) != invert;
	}
	
	public void removeArg(FilterArg arg) {
		desc.remove(arg);
	}

	protected abstract boolean test(GameContext context, Player player, Entity entity);

}
