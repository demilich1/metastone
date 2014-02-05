package net.pferdimanzug.hearthstone.analyzer.game.spells.trigger;

import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;
import net.pferdimanzug.hearthstone.analyzer.game.events.IGameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.SpellCastedEvent;

public class SpellCastedTrigger extends GameEventTrigger {

	@Override
	public boolean fire(IGameEvent event, Entity host) {
		SpellCastedEvent spellCastedEvent = (SpellCastedEvent) event;
		return spellCastedEvent.getPlayerId() == host.getOwner();
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.SPELL_CASTED;
	}

}
