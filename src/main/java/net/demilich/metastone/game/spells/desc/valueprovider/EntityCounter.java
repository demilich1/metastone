package net.demilich.metastone.game.spells.desc.valueprovider;

import java.util.List;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.filter.EntityFilter;
import net.demilich.metastone.game.targeting.EntityReference;

public class EntityCounter extends ValueProvider {

	public EntityCounter(ValueProviderDesc desc) {
		super(desc);
	}

	@Override
	protected int provideValue(GameContext context, Player player, Entity target, Entity host) {
		EntityReference source = desc.getSource();
		List<Entity> relevantEntities = context.resolveTarget(player, host, source);
		int count = 0;
		EntityFilter filter = (EntityFilter) desc.get(ValueProviderArg.FILTER);
		for (Entity entity : relevantEntities) {
			if (filter == null || filter.matches(context, player, entity)) {
				count++;
			}
		}
		return count;
	}

}
