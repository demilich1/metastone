package net.pferdimanzug.hearthstone.analyzer.game.behaviour.human;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;

public class HumanTargetOptions {

	private final HumanBehaviour behaviour;
	private final GameContext context;
	private final int playerId;
	private final ActionGroup actionGroup;

	public HumanTargetOptions(HumanBehaviour behaviour, GameContext context, int playerId, ActionGroup actionGroup) {
		this.behaviour = behaviour;
		this.context = context;
		this.playerId = playerId;
		this.actionGroup = actionGroup;
	}

	public ActionGroup getActionGroup() {
		return actionGroup;
	}

	public HumanBehaviour getBehaviour() {
		return behaviour;
	}

	public GameContext getContext() {
		return context;
	}

	public int getPlayerId() {
		return playerId;
	}

}
