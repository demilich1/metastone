package net.pferdimanzug.hearthstone.analyzer.game.events;


import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class PhysicalAttackEvent extends GameEvent {

	private final Entity attacker;
	private final Entity defender;

	public PhysicalAttackEvent(GameContext context, Entity attacker, Entity defender) {
		super(context);
		this.attacker = attacker;
		this.defender = defender;
	}

	@Override
	public GameEventType getEventType() {
		return GameEventType.PHYSICAL_ATTACK;
	}

	public Entity getAttacker() {
		return attacker;
	}

	public Entity getDefender() {
		return defender;
	}

}
