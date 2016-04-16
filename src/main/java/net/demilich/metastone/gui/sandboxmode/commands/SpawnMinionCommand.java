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
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.events.BoardChangedEvent;
import net.demilich.metastone.game.logic.ActionLogic;
import net.demilich.metastone.gui.sandboxmode.SandboxProxy;

public class SpawnMinionCommand extends SimpleCommand<GameNotification> {

	private MinionCard minionCard;

	@Override
	public void execute(INotification<GameNotification> notification) {
		minionCard = (MinionCard) notification.getBody();

		SandboxProxy sandboxProxy = (SandboxProxy) getFacade().retrieveProxy(SandboxProxy.NAME);
		GameAction summonAction = minionCard.play();

		ActionLogic actionLogic = new ActionLogic();
		GameContext context = sandboxProxy.getSandbox();
		Player selectedPlayer = sandboxProxy.getSelectedPlayer();
		List<GameAction> rolledOutActions = new ArrayList<GameAction>();
		actionLogic.rollout(summonAction, context, selectedPlayer, rolledOutActions);
		ActionGroup actionGroup = new ActionGroup(summonAction);
		for (GameAction gameAction : rolledOutActions) {
			actionGroup.add(gameAction);
		}

		HumanTargetOptions targetOptions = new HumanTargetOptions(this::spawnMinion, context, selectedPlayer.getId(), actionGroup);
		sendNotification(GameNotification.SELECT_TARGET, targetOptions);
	}

	private void spawnMinion(GameAction action) {
		SandboxProxy sandboxProxy = (SandboxProxy) getFacade().retrieveProxy(SandboxProxy.NAME);
		GameContext context = sandboxProxy.getSandbox();
		Player selectedPlayer = sandboxProxy.getSelectedPlayer();

		Minion minion = minionCard.summon();
		Actor nextTo = (Actor) context.resolveSingleTarget(action.getTargetKey());
		int index = selectedPlayer.getMinions().indexOf(nextTo);
		context.getLogic().summon(selectedPlayer.getId(), minion, minionCard, index, false);
		
		if (context.ignoreEvents()) {
			context.setIgnoreEvents(false);
			context.fireGameEvent(new BoardChangedEvent(context));
			context.setIgnoreEvents(true);
		}

		sendNotification(GameNotification.UPDATE_SANDBOX_STATE, context);
	}

}
