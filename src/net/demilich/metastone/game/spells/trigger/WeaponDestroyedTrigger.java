package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.events.WeaponDestroyedEvent;

public class WeaponDestroyedTrigger extends GameEventTrigger {

	@Override
	public boolean fire(GameEvent event, Entity host) {
		WeaponDestroyedEvent weaponDestroyedEvent = (WeaponDestroyedEvent) event;
		return weaponDestroyedEvent.getWeapon().getOwner() == host.getOwner();
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.WEAPON_DESTROYED;
	}

}
