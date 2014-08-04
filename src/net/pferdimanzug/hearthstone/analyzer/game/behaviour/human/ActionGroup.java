package net.pferdimanzug.hearthstone.analyzer.game.behaviour.human;

import java.util.ArrayList;
import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;

public class ActionGroup {

	private final GameAction prototype;
	private final List<GameAction> actionsInGroup = new ArrayList<>();

	public ActionGroup(GameAction prototype) {
		this.prototype = prototype;
		add(prototype);
	}

	public void add(GameAction action) {
		actionsInGroup.add(action);
	}

	public List<GameAction> getActionsInGroup() {
		return actionsInGroup;
	}
	
	public GameAction getPrototype() {
		return prototype;
	}

}
