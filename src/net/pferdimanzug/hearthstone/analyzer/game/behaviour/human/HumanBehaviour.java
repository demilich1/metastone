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
	private boolean waitingForInput;
	private Entity selectedTarget;

	public Entity getSelectedTarget() {
		return this.selectedTarget;
	}

	@Override
	public Entity provideTargetFor(Player player, GameAction action) {
		if (action.getValidTargets() == null || action.getValidTargets().isEmpty()) {
			return null;
		}

		waitingForInput = true;
		ApplicationFacade.getInstance().sendNotification(GameNotification.HUMAN_PROMPT_FOR_TARGET,
				new HumanTargetOptions(player, this, action));
		
		while (waitingForInput) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}

		return selectedTarget;
	}

	@Override
	public GameAction requestAction(GameContext context, Player player, List<GameAction> validActions) {
		waitingForInput = true;
		HumanActionOptions options = new HumanActionOptions(this, context, player, validActions);
		ApplicationFacade.getInstance().sendNotification(GameNotification.HUMAN_PROMPT_FOR_ACTION, options);
		while (waitingForInput) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
		return selectedAction;
	}

	public void setSelectedAction(GameAction selectedAction) {
		this.selectedAction = selectedAction;
		waitingForInput = false;
	}
	
	public void setSelectedTarget(Entity selectedTarget) {
		this.selectedTarget = selectedTarget;
		waitingForInput = false;
	}

	@Override
	public String getName() {
		return "Human Controlled";
	}

}
