package net.demilich.metastone.game.spells.desc.valueprovider;

import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.filter.EntityFilter;

public class MinionSummonValueProvider extends ValueProvider {

	public MinionSummonValueProvider(ValueProviderDesc desc) {
		super(desc);
	}

	@Override
	protected int provideValue(GameContext context, Player player, Entity target, Entity host) {
		Map<String, Map<Integer, Integer>> minionIds = player.getStatistics().getMinionsSummoned();
		int count = 0;
		EntityFilter filter = (EntityFilter) desc.get(ValueProviderArg.FILTER);
		for (String minionId : minionIds.keySet()) {
			Entity entity = context.getCardById(minionId);
			if (filter == null || filter.matches(context, player, entity)) {
				for (Integer turn : minionIds.get(minionId).keySet()) {
					count += minionIds.get(minionId).get(turn);
				}
			}
		}
		return count;
	}

}
