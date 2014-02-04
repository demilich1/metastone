package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RemoveTagSpell extends Spell {

	private static Logger logger = LoggerFactory.getLogger(ApplyTagSpell.class);

	private GameTag[] tags;

	public RemoveTagSpell(GameTag... tags) {
		this.tags = tags;
	}

	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
		for (GameTag tag : tags) {
			logger.debug("Removing tag {} from {}", tag, target);
			target.setTag(tag);
		}
	}
}
