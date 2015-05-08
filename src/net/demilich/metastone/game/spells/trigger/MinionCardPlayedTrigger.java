package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.events.SummonEvent;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerDesc;

public class MinionCardPlayedTrigger extends GameEventTrigger {

	public MinionCardPlayedTrigger(EventTriggerDesc desc) {
		super(desc);
	}

	@Override
	protected boolean fire(GameEvent event, Entity host) {
		SummonEvent summonEvent = (SummonEvent) event;
		return summonEvent.getSource() != null && summonEvent.getSource().getCardType() == CardType.MINION;
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.SUMMON;
	}

}
