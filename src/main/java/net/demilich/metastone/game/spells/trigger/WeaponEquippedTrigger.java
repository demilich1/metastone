package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.events.WeaponEquippedEvent;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerDesc;

public class WeaponEquippedTrigger extends GameEventTrigger {

	public WeaponEquippedTrigger(EventTriggerDesc desc) {
		super(desc);
	}

	@Override
	protected boolean fire(GameEvent event, Entity host) {
		WeaponEquippedEvent weaponEquippedEvent = (WeaponEquippedEvent) event;
		
		TargetPlayer targetPlayer = desc.getTargetPlayer();
		int targetPlayerId = weaponEquippedEvent.getWeapon().getOwner();
		if (targetPlayer != null) {
			return determineTargetPlayer(weaponEquippedEvent, targetPlayer, host, targetPlayerId);
		}
		
		return true;
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.WEAPON_EQUIPPED;
	}

}
