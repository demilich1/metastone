package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.events.SpellCastedEvent;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerDesc;

public class SpellCastedTrigger extends GameEventTrigger {

	public SpellCastedTrigger(EventTriggerDesc desc) {
		super(desc);
	}

	@Override
	protected boolean fire(GameEvent event, Entity host) {
		SpellCastedEvent spellCastedEvent = (SpellCastedEvent) event;

		TargetPlayer sourcePlayer = desc.getSourcePlayer();
		if (sourcePlayer != null && !determineTargetPlayer(spellCastedEvent, sourcePlayer, host, getOwner())) {
			return false;
		}

		return true;
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.SPELL_CASTED;
	}

}
