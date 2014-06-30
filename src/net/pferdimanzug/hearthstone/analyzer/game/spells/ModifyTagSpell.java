package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModifyTagSpell extends Spell {

	private static Logger logger = LoggerFactory.getLogger(ModifyTagSpell.class);

	private final GameTag tag;
	private final int delta;

	public ModifyTagSpell(GameTag tag, int delta) {
		this.tag = tag;
		this.delta = delta;
	}

	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
		if (logger.isDebugEnabled()) {
			if (delta > 0) {
				logger.debug("{} gains +{} of {}", new Object[] { target.getName(), delta, tag });
			} else {
				logger.debug("{} looses {} of {}", new Object[] { target.getName(), delta, tag });
			}
		}

		target.modifyTag(tag, delta);
	}

}
