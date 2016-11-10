package com.hiddenswitch.proto3.net.common;

import java.util.List;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.behaviour.human.HumanBehaviour;

public class NetworkHumanBehaviour extends HumanBehaviour {
	@Override
	public GameAction requestAction(GameContext context, Player player, List<GameAction> validActions) {		
		if (context instanceof ServerGameContext){
			ServerGameContext serverContext = (ServerGameContext) context;
			serverContext.requestAction(player, validActions);
			System.out.println("Action requested");

			while (serverContext.pendingAction == null){
				serverContext.cycleLock(); //busy wait
			}
			System.out.println("Action received");

			GameAction action = serverContext.pendingAction.clone();
			serverContext.pendingAction = null;
			return action;
		} else {
			return super.requestAction(context, player, validActions);
		}
	}

}
