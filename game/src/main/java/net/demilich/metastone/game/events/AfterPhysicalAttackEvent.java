package net.demilich.metastone.game.events;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;

public class AfterPhysicalAttackEvent extends GameEvent {

	private final Actor attacker;
	private final Actor defender;
	private final int damageDealt;

	public AfterPhysicalAttackEvent(GameContext context, Actor attacker, Actor defender, int damageDealt) {
		super(context, defender.getOwner(), attacker.getOwner());
		this.attacker = attacker;
		this.defender = defender;
		this.damageDealt = damageDealt;
	}

	public Actor getAttacker() {
		return attacker;
	}

	public int getDamageDealt() {
		return damageDealt;
	}

	public Actor getDefender() {
		return defender;
	}
	
	@Override
	public Entity getEventSource() {
		return getAttacker();
	}

	@Override
	public Entity getEventTarget() {
		return getDefender();
	}

	@Override
	public GameEventType getEventType() {
		return GameEventType.AFTER_PHYSICAL_ATTACK;
	}

}
