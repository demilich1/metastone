package net.demilich.metastone.game.spells.desc.condition;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.TargetPlayer;

public class OwnedByPlayerCondition extends Condition {

	public OwnedByPlayerCondition(ConditionDesc desc) {
		super(desc);
	}

	@Override
	protected boolean isFulfilled(GameContext context, Player player, ConditionDesc desc, Entity target) {
		TargetPlayer targetPlayer = (TargetPlayer) desc.get(ConditionArg.TARGET_PLAYER);
		switch (targetPlayer) {
		case BOTH:
			return true;
		case OPPONENT:
			return target.getOwner() != player.getId();
		case SELF:
			return target.getOwner() == player.getId();
		default:
			break;

		}
		return false;
	}

}
