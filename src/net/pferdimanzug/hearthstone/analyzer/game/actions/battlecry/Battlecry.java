package net.pferdimanzug.hearthstone.analyzer.game.actions.battlecry;

import net.pferdimanzug.hearthstone.analyzer.game.actions.ActionType;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;

public abstract class Battlecry extends GameAction {
	
	public Battlecry() {
		setActionType(ActionType.MINION_ABILITY);
	}
}
