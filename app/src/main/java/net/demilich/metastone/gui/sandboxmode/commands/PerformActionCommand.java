package net.demilich.metastone.gui.sandboxmode.commands;

import java.util.ArrayList;
import java.util.List;

import net.demilich.nittygrittymvc.SimpleCommand;
import net.demilich.nittygrittymvc.interfaces.INotification;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.behaviour.human.ActionGroup;
import net.demilich.metastone.game.behaviour.human.HumanTargetOptions;
import net.demilich.metastone.game.logic.ActionLogic;
import net.demilich.metastone.game.targeting.TargetSelection;
import net.demilich.metastone.gui.sandboxmode.SandboxProxy;

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
		if (rolledOutActions.isEmpty()) {
			return;
		}
		if (gameAction.getTargetRequirement() != TargetSelection.NONE && gameAction.getTargetRequirement() != TargetSelection.AUTO) {
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
		action.setSource(selectedPlayer.getReference());

		context.getLogic().performGameAction(selectedPlayer.getId(), action);
		sendNotification(GameNotification.UPDATE_SANDBOX_STATE, context);
	}

}
