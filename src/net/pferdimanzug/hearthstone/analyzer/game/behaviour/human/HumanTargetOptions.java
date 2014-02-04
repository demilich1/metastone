package net.pferdimanzug.hearthstone.analyzer.game.behaviour.human;

import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;

public class HumanTargetOptions {

	private final HumanBehaviour behaviour;
	private final GameAction action;
	
	public HumanTargetOptions(HumanBehaviour behaviour, GameAction action) {
		this.behaviour = behaviour;
		this.action = action;
		
	}

	public HumanBehaviour getBehaviour() {
		return behaviour;
	}

	public GameAction getAction() {
		return action;
	}
}
