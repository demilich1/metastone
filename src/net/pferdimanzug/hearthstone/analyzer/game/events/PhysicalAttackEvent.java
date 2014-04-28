package net.pferdimanzug.hearthstone.analyzer.game.events;


import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;

public class PhysicalAttackEvent extends GameEvent {

	private final Actor attacker;
	private final Actor defender;
	private final int damageDealt;

	public PhysicalAttackEvent(GameContext context, Actor attacker, Actor defender, int damageDealt) {
		super(context);
		this.attacker = attacker;
		this.defender = defender;
		this.damageDealt = damageDealt;
	}

	public Actor getAttacker() {
		return attacker;
	}

	public Actor getDefender() {
		return defender;
	}

	@Override
	public GameEventType getEventType() {
		return GameEventType.PHYSICAL_ATTACK;
	}

	public int getDamageDealt() {
		return damageDealt;
	}

}
