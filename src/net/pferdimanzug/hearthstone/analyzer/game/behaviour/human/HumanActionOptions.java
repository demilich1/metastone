package net.pferdimanzug.hearthstone.analyzer.game.behaviour.human;

import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;

public class HumanActionOptions {

	private final HumanBehaviour behaviour;
	private final GameContext context;
	private final Player player;
	private final List<GameAction> validActions;

	public HumanActionOptions(HumanBehaviour behaviour, GameContext context, Player player, List<GameAction> validActions) {
		this.behaviour = behaviour;
		this.context = context;
		this.player = player;
		this.validActions = validActions;
	}

	public GameContext getContext() {
		return context;
	}

	public Player getPlayer() {
		return player;
	}

	public List<GameAction> getValidActions() {
		return validActions;
	}

	public HumanBehaviour getBehaviour() {
		return behaviour;
	}

}
