package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.events.AfterSummonEvent;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerDesc;

public class AfterMinionPlayedTrigger extends AfterMinionSummonedTrigger {
	public AfterMinionPlayedTrigger(EventTriggerDesc desc) {
		super(desc);
	}

	@Override
	protected boolean fire(GameEvent event, Entity host) {
		AfterSummonEvent summonEvent = (AfterSummonEvent) event;

		// when source card is null, then this minion not played as a minion
		// card
		if (summonEvent.getSource() == null) {
			return false;
		}
		return super.fire(summonEvent, host);
	}

}
