package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnStartTrigger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplyTagSpell extends Spell {

	private static Logger logger = LoggerFactory.getLogger(ApplyTagSpell.class);

	private final GameTag tag;
	private final boolean temporary;

	public ApplyTagSpell(GameTag tag) {
		this(tag, false);
	}
	
	public ApplyTagSpell(GameTag tag, boolean temporary) {
		this.tag = tag;
		this.temporary = temporary;
	}

	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
		logger.debug("Applying tag {} to {}", tag, target);
		target.setTag(tag);
		
		if (temporary) {
				RemoveTagSpell debuff = new RemoveTagSpell(tag);
				debuff.setTarget(target.getReference());
				SpellTrigger removeTrigger = new SpellTrigger(new TurnStartTrigger(), debuff, true);
				removeTrigger.setHost(target);
				removeTrigger.setOwner(target.getOwner());
				context.addTrigger(removeTrigger);
		}
	}
}
