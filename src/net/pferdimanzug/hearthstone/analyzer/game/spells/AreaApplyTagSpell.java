package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class AreaApplyTagSpell extends AreaSpell {

	private final GameTag tag;

	public AreaApplyTagSpell(TargetSelection targetSelection, GameTag tag) {
		super(targetSelection);
		this.tag = tag;
	}

	@Override
	protected void forEachTarget(GameContext context, Player player, Entity entity) {
		entity.setTag(tag);
	}

}
