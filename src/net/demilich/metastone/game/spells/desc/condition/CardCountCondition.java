package net.demilich.metastone.game.spells.desc.condition;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.SpellUtils;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.filter.Operation;

public class CardCountCondition extends Condition {

	public CardCountCondition(ConditionDesc desc) {
		super(desc);
	}

	@Override
	protected boolean isFulfilled(GameContext context, Player player, ConditionDesc desc, Entity target) {
		TargetPlayer targetPlayer = desc.contains(ConditionArg.TARGET_PLAYER) ? (TargetPlayer) desc.get(ConditionArg.TARGET_PLAYER) : TargetPlayer.SELF;
		
		int cardCount = 0;
		switch (targetPlayer) {
		case BOTH:
			cardCount = player.getHand().getCount() + context.getOpponent(player).getHand().getCount();
			break;
		case OPPONENT:
			cardCount = context.getOpponent(player).getHand().getCount();
			break;
		case SELF:
			cardCount = player.getHand().getCount();
			break;
		case ACTIVE:
			cardCount = context.getActivePlayer().getHand().getCount();
			break;
		case INACTIVE:
			cardCount = context.getOpponent(context.getActivePlayer()).getHand().getCount();
			break;
		default:
			break;
		
		}
		int targetValue = desc.getInt(ConditionArg.VALUE);
		Operation operation = (Operation) desc.get(ConditionArg.OPERATION);
		return SpellUtils.evaluateOperation(operation, cardCount, targetValue);
	}

}
