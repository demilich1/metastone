package net.pferdimanzug.hearthstone.analyzer.game.spells;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class ReturnMinionToHandSpell extends Spell {
	
	private static Logger logger = LoggerFactory.getLogger(ReturnMinionToHandSpell.class);

	@Override
	protected void onCast(GameContext context, Player player, Entity minion) {
		logger.debug("{} is returned to {}'s hand", minion, player.getName());
		player.getMinions().remove(minion);
		Player owner = context.getPlayer(minion.getOwner());
		context.getLogic().receiveCard(owner, minion.getSourceCard());
	}

}
