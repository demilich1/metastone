package net.pferdimanzug.hearthstone.analyzer.game.spells;

import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.ActionType;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class AreaHealingSpell extends HealingSpell {
	
	private TargetSelection targetSelection;

	public AreaHealingSpell(int healing, TargetSelection targetSelection) {
		super(healing);
		this.targetSelection = targetSelection;
	}

	@Override
	public void cast(GameContext context, Player player, Entity target) {
		GameAction dummyTargetAction = new DummyTargetAction(targetSelection);
		
		List<Entity> targets = context.getLogic().getValidTargets(player, dummyTargetAction);
		for (Entity entity : targets) {
			context.getLogic().damage(entity, getHealing());
		}
	}
	
	private class DummyTargetAction extends GameAction {
		
		public DummyTargetAction(TargetSelection targetRequirement) {
			setTargetRequirement(targetRequirement);
			setActionType(ActionType.UNDEFINED);
		}

		@Override
		public void execute(GameContext context, Player player) {}
		
	}

}
