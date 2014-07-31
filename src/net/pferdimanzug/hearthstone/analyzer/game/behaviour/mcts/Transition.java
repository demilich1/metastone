package net.pferdimanzug.hearthstone.analyzer.game.behaviour.mcts;

import java.util.ArrayList;
import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

class Transition {

	private final GameAction action;
	private final Entity target;

	public Transition(GameAction action, Entity target) {
		this.action = action;
		this.target = target;
	}

	public GameAction getAction() {
		return action;
	}

	public Entity getTarget() {
		return target;
	}

	public static List<Transition> generate(List<GameAction> gameActions) {
		List<Transition> transitions = new ArrayList<Transition>();
		for (GameAction gameAction : gameActions) {
			if (gameAction == null || gameAction.getValidTargets() == null || gameAction.getValidTargets().isEmpty()) {
				transitions.add(new Transition(gameAction, null));
			} else {
				for (Entity target : gameAction.getValidTargets()) {
					transitions.add(new Transition(gameAction, target));
				}
			}
		}
		return transitions;
	}

}
