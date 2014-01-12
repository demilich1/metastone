package net.pferdimanzug.hearthstone.analyzer.game.spells;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class MindControlSpell extends Spell {
	private static Logger logger = LoggerFactory.getLogger(MindControlSpell.class);

	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
		Minion minion = (Minion) target;
		logger.debug("{} mind controls {}", player.getName(), target);
		context.getOpponent(player).getMinions().remove(minion);
		player.getMinions().add(minion);
		minion.setOwner(player);
	}

}
