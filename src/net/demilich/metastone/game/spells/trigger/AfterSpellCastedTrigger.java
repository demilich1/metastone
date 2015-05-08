package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.events.AfterSpellCastedEvent;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerDesc;

public class AfterSpellCastedTrigger extends GameEventTrigger {

	public AfterSpellCastedTrigger(EventTriggerDesc desc) {
		super(desc);
	}

	@Override
	protected boolean fire(GameEvent event, Entity host) {
		AfterSpellCastedEvent spellCastedEvent = (AfterSpellCastedEvent) event;
		TargetPlayer targetPlayer = desc.getTargetPlayer();
		switch (targetPlayer) {
		case BOTH:
			return true;
		case SELF:
			return spellCastedEvent.getPlayerId() == host.getOwner();
		case OPPONENT:
			return spellCastedEvent.getPlayerId() != host.getOwner();
		}
		return false;
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.AFTER_SPELL_CASTED;
	}

}
