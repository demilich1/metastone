package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.events.SpellCastedEvent;
import net.demilich.metastone.game.spells.TargetPlayer;

public class SpellCastedTrigger extends GameEventTrigger {

	private final TargetPlayer targetPlayer;

	public SpellCastedTrigger(TargetPlayer targetPlayer) {
		this.targetPlayer = targetPlayer;
	}

	@Override
	public boolean fire(GameEvent event, Entity host) {
		SpellCastedEvent spellCastedEvent = (SpellCastedEvent) event;
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
		return GameEventType.SPELL_CASTED;
	}

}
