package net.pferdimanzug.hearthstone.analyzer.game.actions.battlecry;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;

public class BattlecryDrawCard extends Battlecry {

	@Override
	public void execute(GameContext context, Player player) {
		context.getLogic().drawCard(player);
	}

}
