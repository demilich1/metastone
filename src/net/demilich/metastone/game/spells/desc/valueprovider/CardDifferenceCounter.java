package net.demilich.metastone.game.spells.desc.valueprovider;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;

public class CardDifferenceCounter extends ValueProvider {

	public CardDifferenceCounter(ValueProviderDesc desc) {
		super(desc);
	}

	@Override
	protected int provideValue(GameContext context, Player player, Entity target) {
		Player opponent = context.getOpponent(player);
		int cardDifference = opponent.getHand().getCount() - player.getHand().getCount();
		if (cardDifference <= 0) {
			return 0;
		}
		return cardDifference;
	}

}
