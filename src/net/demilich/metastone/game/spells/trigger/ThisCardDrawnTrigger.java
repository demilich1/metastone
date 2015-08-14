package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.events.DrawCardEvent;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerDesc;

public class ThisCardDrawnTrigger extends GameEventTrigger {

	public ThisCardDrawnTrigger(EventTriggerDesc desc) {
		super(desc);
	}

	@Override
	protected boolean fire(GameEvent event, Entity host) {
		DrawCardEvent drawCardEvent = (DrawCardEvent) event;
		return host == drawCardEvent.getCard();
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.DRAW_CARD;
	}

}
