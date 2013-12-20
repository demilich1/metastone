package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class AreaHealingSpell extends AreaSpell {

	private int healing;

	public AreaHealingSpell(int healing, TargetSelection targetSelection) {
		super(targetSelection);
		this.healing = healing;
	}

	@Override
	protected void forEachTarget(GameContext context, Player player, Entity entity) {
		context.getLogic().heal(entity, healing);
	}

	

}
