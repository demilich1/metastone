package net.demilich.metastone.game.spells.desc.valueprovider;

import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.filter.EntityFilter;

public class MinionSummonValueProvider extends ValueProvider {

	public MinionSummonValueProvider(ValueProviderDesc desc) {
		super(desc);
	}

	@Override
	protected int provideValue(GameContext context, Player player, Entity target, Entity host) {
		Map<String, Integer> minionIds = player.getStatistics().getMinionsSummoned();
		int count = 0;
		EntityFilter filter = (EntityFilter) desc.get(ValueProviderArg.FILTER);
		for (String minionId : minionIds.keySet()) {
			Entity entity = CardCatalogue.getCardById(minionId);
			if (filter == null || filter.matches(context, player, entity)) {
				count += minionIds.get(minionId);
			}
		}
		return count;
	}

}
