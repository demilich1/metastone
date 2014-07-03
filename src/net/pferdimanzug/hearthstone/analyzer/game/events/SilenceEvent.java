package net.pferdimanzug.hearthstone.analyzer.game.events;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class SilenceEvent extends GameEvent {

	private final Minion target;

	public SilenceEvent(GameContext context, Minion target) {
		super(context);
		this.target = target;
	}

	@Override
	public Entity getEventTarget() {
		return getTarget();
	}

	@Override
	public GameEventType getEventType() {
		return GameEventType.SILENCE;
	}

	public Minion getTarget() {
		return target;
	}

}
