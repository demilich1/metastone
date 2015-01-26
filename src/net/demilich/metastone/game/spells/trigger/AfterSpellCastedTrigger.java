package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.events.AfterSpellCastedEvent;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.spells.TargetPlayer;

public class AfterSpellCastedTrigger extends GameEventTrigger {

	private final TargetPlayer targetPlayer;

	public AfterSpellCastedTrigger(TargetPlayer targetPlayer) {
		this.targetPlayer = targetPlayer;
	}

	@Override
	public boolean fire(GameEvent event, Entity host) {
		AfterSpellCastedEvent spellCastedEvent = (AfterSpellCastedEvent) event;
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
