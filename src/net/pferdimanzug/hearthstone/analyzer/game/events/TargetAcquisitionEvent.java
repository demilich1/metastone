package net.pferdimanzug.hearthstone.analyzer.game.events;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.TargetAcquisition;

public class TargetAcquisitionEvent extends GameEvent {

	private final TargetAcquisition targetAcquisition;

	public TargetAcquisitionEvent(GameContext context, TargetAcquisition targetAcquisition) {
		super(context);
		this.targetAcquisition = targetAcquisition;
	}

	@Override
	public GameEventType getEventType() {
		return GameEventType.TARGET_ACQUISITION;
	}

	public TargetAcquisition getTargetAcquisition() {
		return targetAcquisition;
	}

}
