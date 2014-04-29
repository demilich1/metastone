package net.pferdimanzug.hearthstone.analyzer.game;

import net.pferdimanzug.hearthstone.analyzer.game.actions.ActionType;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;

public class PhysicalAttackTargetAcquisition extends TargetAcquisition {

	private final Actor attacker;

	public PhysicalAttackTargetAcquisition(Actor target, Actor attacker) {
		super(ActionType.PHYSICAL_ATTACK, target);
		this.attacker = attacker;
	}

	public Actor getAttacker() {
		return attacker;
	}

}
