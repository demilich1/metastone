package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class ReturnMinionToHandSpell implements ISpell {

	@Override
	public void cast(GameContext context, Player player, Entity target) {
		Entity minion = (Entity) target;
		player.getMinions().remove(minion);
		context.getLogic().receiveCard(player, minion.getSourceCard());
	}

}
