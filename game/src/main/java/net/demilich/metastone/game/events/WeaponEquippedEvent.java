package net.demilich.metastone.game.events;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.weapons.Weapon;

public class WeaponEquippedEvent extends GameEvent {
	private final Weapon weapon;

	public WeaponEquippedEvent(GameContext context, Weapon weapon) {
		super(context, weapon.getOwner(), -1);
		this.weapon = weapon;
	}
	
	@Override
	public Entity getEventTarget() {
		return getWeapon();
	}

	@Override
	public GameEventType getEventType() {
		return GameEventType.WEAPON_EQUIPPED;
	}

	public Weapon getWeapon() {
		return weapon;
	}
}
