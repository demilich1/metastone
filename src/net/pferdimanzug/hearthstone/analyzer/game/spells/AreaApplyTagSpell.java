package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AreaApplyTagSpell extends AreaSpell {

	private static Logger logger = LoggerFactory.getLogger(AreaApplyTagSpell.class);

	private final GameTag tag;

	public AreaApplyTagSpell(TargetSelection targetSelection, GameTag tag) {
		super(targetSelection);
		this.tag = tag;
	}

	@Override
	protected void forEachTarget(GameContext context, Player player, Entity entity) {
		logger.debug("Applying tag {} to {}", tag, entity);
		entity.setTag(tag);
	}

}
