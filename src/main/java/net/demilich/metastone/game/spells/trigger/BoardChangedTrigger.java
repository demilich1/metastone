package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerDesc;

public class BoardChangedTrigger extends GameEventTrigger {

	public BoardChangedTrigger() {
		this(EventTriggerDesc.createEmpty(BoardChangedTrigger.class));
	}

	public BoardChangedTrigger(EventTriggerDesc desc) {
		super(desc);
	}

	@Override
	protected boolean fire(GameEvent event, Entity host) {
		return true;
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.BOARD_CHANGED;
	}

}
