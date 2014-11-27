package net.pferdimanzug.hearthstone.analyzer.game.spells.trigger;

import net.pferdimanzug.hearthstone.analyzer.game.Environment;
import net.pferdimanzug.hearthstone.analyzer.game.actions.ActionType;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;

public class FumbleTrigger extends TargetAcquisitionTrigger {

	public FumbleTrigger() {
		super(ActionType.PHYSICAL_ATTACK);
	}

	@Override
	public boolean fire(GameEvent event, Entity host) {
		if (!super.fire(event, host)) {
			return false;
		}
		Entity attacker = (Entity) event.getGameContext().getEnvironment().get(Environment.ATTACKER);
		if (attacker != host) {
			return false;
		}
		
		// this trigger only sometimes fires
		return event.getGameContext().getLogic().randomBool();
	}
}