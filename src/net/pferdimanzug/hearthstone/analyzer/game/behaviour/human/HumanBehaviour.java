package net.pferdimanzug.hearthstone.analyzer.game.behaviour.human;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import net.pferdimanzug.hearthstone.analyzer.ApplicationFacade;
import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.IBehaviour;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class HumanBehaviour implements IBehaviour {
	
	private GameAction selectedAction;

	@Override
	public Entity provideTargetFor(Player player, GameAction action, List<Entity> validTargets) {
		//TODO: copied from PlayRandomBehaviour, change!
		if (validTargets.isEmpty()) {
			return null;
		}

		Entity randomTarget = validTargets.get(ThreadLocalRandom.current().nextInt(validTargets.size()));
		return randomTarget;
	}

	@Override
	public GameAction requestAction(GameContext context, Player player, List<GameAction> validActions) {
		selectedAction = null;
		HumanActionOptions options = new HumanActionOptions(this, context, player, validActions);
		ApplicationFacade.getInstance().sendNotification(GameNotification.HUMAN_PROMPT_FOR_ACTION, options);
		while (selectedAction == null) {
			System.out.println("Waiting for human action...");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
		return selectedAction;
	}

	public void setSelectedAction(GameAction selectedAction) {
		this.selectedAction = selectedAction;
	}

}
