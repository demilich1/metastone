package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.SummonEvent;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerDesc;

public class MinionPlayedTrigger extends MinionSummonedTrigger {
	public MinionPlayedTrigger(EventTriggerDesc desc) {
		super(desc);
	}

	@Override
	protected boolean fire(GameEvent event, Entity host) {
		SummonEvent summonEvent = (SummonEvent) event;

		// when source card is null, then this minion not played as a minion
		// card
		if (summonEvent.getSource() == null) {
			return false;
		}
		return super.fire(summonEvent, host);
	}

}
