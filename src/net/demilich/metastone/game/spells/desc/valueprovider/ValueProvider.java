package net.demilich.metastone.game.spells.desc.valueprovider;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.TargetPlayer;

public abstract class ValueProvider {

	protected final ValueProviderDesc desc;

	public ValueProvider(ValueProviderDesc desc) {
		this.desc = desc;
	}

	public int getValue(GameContext context, Player player, Entity target) {
		TargetPlayer targetPlayer = (TargetPlayer) desc.get(ValueProviderArg.TARGET_PLAYER);
		Player providingPlayer = targetPlayer == null || targetPlayer == TargetPlayer.SELF ? player : context.getOpponent(player);
		int multiplier = desc.contains(ValueProviderArg.MULTIPLIER) ? desc.getInt(ValueProviderArg.MULTIPLIER) : 1;
		int offset = desc.contains(ValueProviderArg.OFFSET) ? desc.getInt(ValueProviderArg.OFFSET) : 0;
		int value = provideValue(context, providingPlayer, target) * multiplier + offset;
		return value;
	}

	protected abstract int provideValue(GameContext context, Player player, Entity target);

}
