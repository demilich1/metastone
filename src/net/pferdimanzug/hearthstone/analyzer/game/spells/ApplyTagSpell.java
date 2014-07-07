package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.GameEventTrigger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplyTagSpell extends RevertableSpell {

	private static Logger logger = LoggerFactory.getLogger(ApplyTagSpell.class);

	private final GameTag tag;

	public ApplyTagSpell(GameTag tag) {
		this(tag, null);
	}

	public ApplyTagSpell(GameTag tag, GameEventTrigger removeTrigger) {
		super(removeTrigger);
		this.tag = tag;
	}

	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
		logger.debug("Applying tag {} to {}", tag, target);
		target.setTag(tag);
		super.onCast(context, player, target);
	}

	@Override
	protected Spell getReverseSpell() {
		return new RemoveTagSpell(tag);
	}
}
