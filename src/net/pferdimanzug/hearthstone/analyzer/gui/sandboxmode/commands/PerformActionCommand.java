package net.pferdimanzug.hearthstone.analyzer.gui.sandboxmode.commands;

import java.util.ArrayList;
import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.human.ActionGroup;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.human.HumanTargetOptions;
import net.pferdimanzug.hearthstone.analyzer.game.logic.ActionLogic;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.gui.sandboxmode.SandboxProxy;
import de.pferdimanzug.nittygrittymvc.SimpleCommand;
import de.pferdimanzug.nittygrittymvc.interfaces.INotification;

public class PerformActionCommand extends SimpleCommand<GameNotification> {

	@Override
	public void execute(INotification<GameNotification> notification) {
		SandboxProxy sandboxProxy = (SandboxProxy) getFacade().retrieveProxy(SandboxProxy.NAME);
		GameAction gameAction = (GameAction) notification.getBody();

		ActionLogic actionLogic = new ActionLogic();
		GameContext context = sandboxProxy.getSandbox();
		Player selectedPlayer = sandboxProxy.getSelectedPlayer();
		List<GameAction> rolledOutActions = new ArrayList<GameAction>();
		actionLogic.rollout(gameAction, context, selectedPlayer, rolledOutActions);
		if (gameAction.getTargetRequirement() != TargetSelection.NONE) {
			ActionGroup actionGroup = new ActionGroup(rolledOutActions.get(0));
			for (GameAction rolledAction : rolledOutActions) {
				actionGroup.add(rolledAction);
			}
			HumanTargetOptions targetOptions = new HumanTargetOptions(this::performAction, context, selectedPlayer.getId(), actionGroup);
			sendNotification(GameNotification.SELECT_TARGET, targetOptions);
		} else {
			performAction(gameAction);
		}

	}

	private void performAction(GameAction action) {
		SandboxProxy sandboxProxy = (SandboxProxy) getFacade().retrieveProxy(SandboxProxy.NAME);
		GameContext context = sandboxProxy.getSandbox();
		Player selectedPlayer = sandboxProxy.getSelectedPlayer();

		context.getLogic().performGameAction(selectedPlayer.getId(), action);
		sendNotification(GameNotification.UPDATE_SANDBOX_STATE, context);
	}

}
