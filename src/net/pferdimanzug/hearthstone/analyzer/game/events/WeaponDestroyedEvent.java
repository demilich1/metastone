package net.pferdimanzug.hearthstone.analyzer.game.events;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.weapons.Weapon;

public class WeaponDestroyedEvent extends GameEvent {

	private final Weapon weapon;

	public WeaponDestroyedEvent(GameContext context, Weapon weapon) {
		super(context);
		this.weapon = weapon;
	}

	@Override
	public Entity getEventTarget() {
		return getWeapon();
	}

	@Override
	public GameEventType getEventType() {
		return GameEventType.WEAPON_DESTROYED;
	}

	public Weapon getWeapon() {
		return weapon;
	}

}
