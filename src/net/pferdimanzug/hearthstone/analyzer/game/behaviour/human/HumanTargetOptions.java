package net.pferdimanzug.hearthstone.analyzer.game.behaviour.human;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.actions.IActionSelectionListener;

public class HumanTargetOptions {

	private final IActionSelectionListener actionSelectionListener;
	private final GameContext context;
	private final int playerId;
	private final ActionGroup actionGroup;

	public HumanTargetOptions(IActionSelectionListener actionSelectionListener, GameContext context, int playerId, ActionGroup actionGroup) {
		this.actionSelectionListener = actionSelectionListener;
		this.context = context;
		this.playerId = playerId;
		this.actionGroup = actionGroup;
	}

	public ActionGroup getActionGroup() {
		return actionGroup;
	}

	public IActionSelectionListener getActionSelectionListener() {
		return actionSelectionListener;
	}

	public GameContext getContext() {
		return context;
	}

	public int getPlayerId() {
		return playerId;
	}

}
