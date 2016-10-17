package net.demilich.metastone.game.spells.desc.filter;

import java.io.Serializable;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.TargetPlayer;

public abstract class EntityFilter implements Serializable {

	protected final FilterDesc desc;

	public EntityFilter(FilterDesc desc) {
		this.desc = desc;
	}
	
	public Object getArg(FilterArg arg) {
		return desc.get(arg);
	}
	
	public boolean hasArg(FilterArg arg) {
		return desc.contains(arg);
	}

	public boolean matches(GameContext context, Player player, Entity entity) {
		boolean invert = desc.getBool(FilterArg.INVERT);
		TargetPlayer targetPlayer = (TargetPlayer) desc.get(FilterArg.TARGET_PLAYER);
		if (targetPlayer == null) {
			targetPlayer = TargetPlayer.SELF;
		}
		Player providingPlayer = null;
		switch (targetPlayer) {
		case ACTIVE:
			providingPlayer = context.getActivePlayer();
			break;
		case BOTH:
			boolean test = false;
			for (Player selectedPlayer : context.getPlayers()) {
				test |= (this.test(context, selectedPlayer, entity) != invert);
			}
			return test;
		case INACTIVE:
			providingPlayer = context.getOpponent(context.getActivePlayer());
			break;
		case OPPONENT:
			providingPlayer = context.getOpponent(player);
			break;
		case OWNER:
			providingPlayer = context.getPlayer(entity.getOwner());
			break;
		case SELF:
		default:
			providingPlayer = player;
			break;
		}
		return this.test(context, providingPlayer, entity) != invert;
	}

	protected abstract boolean test(GameContext context, Player player, Entity entity);

}
