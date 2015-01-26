package net.demilich.metastone.game.behaviour.value;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.entities.minions.Minion;

public class SpellBreakerValueApproximator extends BasicMinionValueApproximator {

	private float getBestBattlecryValue(GameContext context, Player player, float bestValueSoFar) {
		float bestValue = bestValueSoFar;

		for (Minion minion : player.getMinions()) {
			float value = Values.getSilenceScore(context, minion);
			if (value > bestValue) {
				bestValue = value;
			}
		}
		return bestValue;
	}

	@Override
	public float getValue(GameContext context, GameAction action, int playerId) {
		Player player = context.getPlayer(playerId);
		float battlecryValue = getBestBattlecryValue(context, player, -5);
		Player opponent = context.getOpponent(player);
		battlecryValue = getBestBattlecryValue(context, opponent, battlecryValue);
		return super.getValue(context, action, playerId) + battlecryValue;
	}

}
