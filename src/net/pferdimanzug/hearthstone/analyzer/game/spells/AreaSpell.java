package net.pferdimanzug.hearthstone.analyzer.game.spells;

import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.ActionType;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public abstract class AreaSpell implements ISpell {
	
	private TargetSelection targetSelection;

	public AreaSpell(TargetSelection targetSelection) {
		this.targetSelection = targetSelection;
	}

	@Override
	public void cast(GameContext context, Player player, Entity target) {
		GameAction dummyTargetAction = new DummyTargetAction(targetSelection);
		
		List<Entity> targets = context.getLogic().getValidTargets(player, dummyTargetAction);
		for (Entity entity : targets) {
			forEachTarget(context, player, entity);
		}
	}
	
	protected abstract void forEachTarget(GameContext context, Player player, Entity entity);
	
	private class DummyTargetAction extends GameAction {
		
		public DummyTargetAction(TargetSelection targetRequirement) {
			setTargetRequirement(targetRequirement);
			setActionType(ActionType.UNDEFINED);
		}

		@Override
		public void execute(GameContext context, Player player) {
			
		}
		
	}

}
