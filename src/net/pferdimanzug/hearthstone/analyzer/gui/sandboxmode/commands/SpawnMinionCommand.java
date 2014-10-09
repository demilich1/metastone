package net.pferdimanzug.hearthstone.analyzer.gui.sandboxmode.commands;

import java.util.ArrayList;
import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.human.ActionGroup;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.human.HumanTargetOptions;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.logic.ActionLogic;
import net.pferdimanzug.hearthstone.analyzer.gui.sandboxmode.SandboxProxy;
import de.pferdimanzug.nittygrittymvc.SimpleCommand;
import de.pferdimanzug.nittygrittymvc.interfaces.INotification;

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
		context.getLogic().summon(selectedPlayer.getId(), minion, minionCard, nextTo, false);
		
		sendNotification(GameNotification.UPDATE_SANDBOX_STATE, context);
	}

}
