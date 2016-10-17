package net.demilich.metastone.game.spells.desc.valueprovider;

import java.io.Serializable;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.TargetPlayer;

public abstract class ValueProvider implements Serializable {

	protected final ValueProviderDesc desc;

	public ValueProvider(ValueProviderDesc desc) {
		this.desc = desc;
	}

	public int getValue(GameContext context, Player player, Entity target, Entity host) {
		TargetPlayer targetPlayer = (TargetPlayer) desc.get(ValueProviderArg.TARGET_PLAYER);
		if (targetPlayer == null) {
			targetPlayer = TargetPlayer.SELF;
		}
		Player providingPlayer = null;
		switch (targetPlayer) {
		case ACTIVE:
			providingPlayer = context.getActivePlayer();
			break;
		case BOTH:
			int multiplier = desc.contains(ValueProviderArg.MULTIPLIER) ? desc.getInt(ValueProviderArg.MULTIPLIER) : 1;
			int offset = desc.contains(ValueProviderArg.OFFSET) ? desc.getInt(ValueProviderArg.OFFSET) : 0;
			int value = 0;
			for (Player selectedPlayer : context.getPlayers()) {
				value += provideValue(context, selectedPlayer, target, host);
			}
			value = value * multiplier + offset;
			return value;
		case INACTIVE:
			providingPlayer = context.getOpponent(context.getActivePlayer());
			break;
		case OPPONENT:
			providingPlayer = context.getOpponent(player);
			break;
		case OWNER:
			providingPlayer = context.getPlayer(host.getOwner());
			break;
		case SELF:
		default:
			providingPlayer = player;
			break;
		}
		int multiplier = desc.contains(ValueProviderArg.MULTIPLIER) ? desc.getInt(ValueProviderArg.MULTIPLIER) : 1;
		int offset = desc.contains(ValueProviderArg.OFFSET) ? desc.getInt(ValueProviderArg.OFFSET) : 0;
		int value = provideValue(context, providingPlayer, target, host) * multiplier + offset;
		return value;
	}

	protected abstract int provideValue(GameContext context, Player player, Entity target, Entity host);

}
