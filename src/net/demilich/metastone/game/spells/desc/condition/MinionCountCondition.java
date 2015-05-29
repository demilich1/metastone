package net.demilich.metastone.game.spells.desc.condition;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.SpellUtils;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.filter.Operation;

public class MinionCountCondition extends Condition {

	public MinionCountCondition(ConditionDesc desc) {
		super(desc);
	}

	@Override
	protected boolean isFulfilled(GameContext context, Player player, ConditionDesc desc, Entity target) {
		TargetPlayer targetPlayer = desc.contains(ConditionArg.TARGET_PLAYER) ? (TargetPlayer) desc.get(ConditionArg.TARGET_PLAYER) : TargetPlayer.SELF;
		
		int minionCount = 0;
		switch (targetPlayer) {
		case BOTH:
			minionCount = context.getTotalMinionCount();
			break;
		case OPPONENT:
			minionCount = context.getOpponent(player).getMinions().size();
			break;
		case SELF:
			minionCount = player.getMinions().size();
			break;
		default:
			break;
		
		}
		int targetValue = desc.getInt(ConditionArg.VALUE);
		Operation operation = (Operation) desc.get(ConditionArg.OPERATION);
		return SpellUtils.evaluateOperation(operation, minionCount, targetValue);
	}

}
