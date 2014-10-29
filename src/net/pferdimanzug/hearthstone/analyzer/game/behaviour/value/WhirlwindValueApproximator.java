package net.pferdimanzug.hearthstone.analyzer.game.behaviour.value;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class WhirlwindValueApproximator implements IValueApproximator {

	@Override
	public float getValue(GameContext context, GameAction action, int playerId) {
		float value = 0;
		Player player = context.getPlayer(playerId);
		Player opponent = context.getOpponent(player);
		for (Minion minion : player.getMinions()) {
			// Acolyte of Pain
			if (minion.getSourceCard().getTypeId() == 79) {
				value += Values.DRAW_CARD_VALUE;
			}
			// Armorsmith
			else if (minion.getSourceCard().getTypeId() == 361) {
				value++;
			}
			if (Divination.willMinionDie(context, minion, 1)) {
				value -= minion.getSourceCard().getBaseManaCost();
			} else {
				value--;
			}
		}
		for (Minion minion : opponent.getMinions()) {
			// Acolyte of Pain
			if (minion.getSourceCard().getTypeId() == 79) {
				value -= Values.DRAW_CARD_VALUE;
			}
			// Armorsmith
			else if (minion.getSourceCard().getTypeId() == 361) {
				value--;
			}
			if (Divination.willMinionDie(context, minion, 1)) {
				value += minion.getSourceCard().getBaseManaCost();
			} else {
				value++;
			}
		}
		return value;
	}

}
