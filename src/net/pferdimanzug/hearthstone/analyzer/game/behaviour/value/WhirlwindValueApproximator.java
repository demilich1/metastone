package net.pferdimanzug.hearthstone.analyzer.game.behaviour.value;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.AcolyteOfPain;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warrior.Armorsmith;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class WhirlwindValueApproximator implements IValueApproximator {
	
	private final Card armorsmith = new Armorsmith();
	private final Card acolyteOfPain = new AcolyteOfPain();

	@Override
	public float getValue(GameContext context, GameAction action, int playerId) {
		float value = 0;
		Player player = context.getPlayer(playerId);
		Player opponent = context.getOpponent(player);
		for (Minion minion : player.getMinions()) {
			if (minion.getSourceCard().getTypeId() == acolyteOfPain.getTypeId()) {
				value += Values.DRAW_CARD_VALUE;
			}
			else if (minion.getSourceCard().getTypeId() == armorsmith.getTypeId()) {
				value++;
			}
			if (Divination.willMinionDie(context, minion, 1)) {
				value -= minion.getSourceCard().getBaseManaCost();
			} else {
				value--;
			}
		}
		for (Minion minion : opponent.getMinions()) {
			if (minion.getSourceCard().getTypeId() == acolyteOfPain.getTypeId()) {
				value -= Values.DRAW_CARD_VALUE;
			}
			else if (minion.getSourceCard().getTypeId() == armorsmith.getTypeId()) {
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
