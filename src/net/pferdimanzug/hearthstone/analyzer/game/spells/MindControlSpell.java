package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MindControlSpell extends Spell {
	private static Logger logger = LoggerFactory.getLogger(MindControlSpell.class);

	@Override
	protected void onCast(GameContext context, Player player, Actor target) {
		Minion minion = (Minion) target;
		logger.debug("{} mind controls {}", player.getName(), target);
		context.getOpponent(player).getMinions().remove(minion);
		player.getMinions().add(minion);
		minion.setOwner(player.getId());
	}

}
