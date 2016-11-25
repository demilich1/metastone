package net.demilich.metastone.game.spells.desc.valueprovider;

import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.filter.EntityFilter;

public class CardsPlayedValueProvider extends ValueProvider {

	public CardsPlayedValueProvider(ValueProviderDesc desc) {
		super(desc);
	}

	@Override
	protected int provideValue(GameContext context, Player player, Entity target, Entity host) {
		Map<String, Integer> cardIds = player.getStatistics().getCardsPlayed();
		int count = 0;
		EntityFilter filter = (EntityFilter) desc.get(ValueProviderArg.FILTER);
		for (String cardId : cardIds.keySet()) {
			Entity entity = context.getCardById(cardId);
			if (filter == null || filter.matches(context, player, entity)) {
				count += cardIds.get(cardId);
			}
		}
		return count;
	}

}
