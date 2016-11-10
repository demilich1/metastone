package net.demilich.metastone.game.spells.desc.condition;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Minion;

public class HasSpecificMinionOnBoardCondition extends Condition {

	public HasSpecificMinionOnBoardCondition(ConditionDesc desc) {
		super(desc);
	}

	@Override
	protected boolean isFulfilled(GameContext context, Player player, ConditionDesc desc, Entity source, Entity target) {
		String cardName = (String) desc.get(ConditionArg.CARD_ID);
		for (Minion minion : player.getMinions()) {
			if (minion.getSourceCard().getCardId().equals(cardName)) {
				return true;
			}
		}
		return false;
	}

}
