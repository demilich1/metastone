package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.GameEventTrigger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModifyTagSpell extends RevertableSpell {

	private static Logger logger = LoggerFactory.getLogger(ModifyTagSpell.class);

	private final GameTag tag;
	private final int delta;

	public ModifyTagSpell(GameTag tag, int delta) {
		this(tag, delta, null);
	}
	
	public ModifyTagSpell(GameTag tag, int delta, GameEventTrigger revertTrigger) {
		this(tag, delta, revertTrigger, null);
	}
	
	public ModifyTagSpell(GameTag tag, int delta, GameEventTrigger revertTrigger, GameEventTrigger secondRevertTrigger) {
		super(revertTrigger, secondRevertTrigger);
		this.tag = tag;
		this.delta = delta;
	}

	@Override
	protected Spell getReverseSpell() {
		return new ModifyTagSpell(tag, -delta);
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
		super.onCast(context, player, target);
	}

}
