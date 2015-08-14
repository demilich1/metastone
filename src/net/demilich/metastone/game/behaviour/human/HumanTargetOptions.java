package net.demilich.metastone.game.behaviour.human;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.actions.IActionSelectionListener;

public class HumanTargetOptions {

	private final IActionSelectionListener actionSelectionListener;
	private final GameContext context;
	private final int playerId;
	private final ActionGroup actionGroup;

	public HumanTargetOptions(IActionSelectionListener actionSelectionListener, GameContext context, int playerId,
			ActionGroup actionGroup) {
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
