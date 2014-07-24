package net.pferdimanzug.hearthstone.analyzer.game.behaviour.human;

import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;

public class HumanTargetOptions {

	private final HumanBehaviour behaviour;
	private final GameAction action;
	private final Player player;
	
	public HumanTargetOptions(Player player, HumanBehaviour behaviour, GameAction action) {
		this.player = player;
		this.behaviour = behaviour;
		this.action = action;
	}

	public GameAction getAction() {
		return action;
	}

	public HumanBehaviour getBehaviour() {
		return behaviour;
	}

	public Player getPlayer() {
		return player;
	}
}
