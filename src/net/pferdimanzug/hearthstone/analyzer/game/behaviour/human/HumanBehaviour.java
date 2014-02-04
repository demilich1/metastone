package net.pferdimanzug.hearthstone.analyzer.game.behaviour.human;

import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.ApplicationFacade;
import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.IBehaviour;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class HumanBehaviour implements IBehaviour {

	private GameAction selectedAction;
	private boolean waitingForAction;
	private Entity selectedTarget;

	@Override
	public Entity provideTargetFor(Player player, GameAction action) {
		if (action.getValidTargets() == null || action.getValidTargets().isEmpty()) {
			return null;
		}

		ApplicationFacade.getInstance().sendNotification(GameNotification.HUMAN_PROMPT_FOR_TARGET,
				new HumanTargetOptions(this, action));

		return selectedTarget;
	}

	@Override
	public GameAction requestAction(GameContext context, Player player, List<GameAction> validActions) {
		waitingForAction = true;
		HumanActionOptions options = new HumanActionOptions(this, context, player, validActions);
		ApplicationFacade.getInstance().sendNotification(GameNotification.HUMAN_PROMPT_FOR_ACTION, options);
		while (waitingForAction) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
		return selectedAction;
	}

	public void setSelectedAction(GameAction selectedAction) {
		this.selectedAction = selectedAction;
		waitingForAction = false;
	}

	public void setSelectedTarget(Entity selectedTarget) {
		this.selectedTarget = selectedTarget;
	}
	
	public Entity getSelectedTarget() {
		return this.selectedTarget;
	}

}
