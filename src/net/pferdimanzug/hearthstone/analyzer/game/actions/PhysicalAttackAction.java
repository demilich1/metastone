package net.pferdimanzug.hearthstone.analyzer.game.actions;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class PhysicalAttackAction extends GameAction {
	
	private final EntityReference attackerReference;

	public PhysicalAttackAction(EntityReference attackerReference) {
		setTargetRequirement(TargetSelection.ENEMY_CHARACTERS);
		setActionType(ActionType.PHYSICAL_ATTACK);
		this.attackerReference = attackerReference;
	}

	@Override
	public void execute(GameContext context, int playerId) {
		Actor defender = (Actor) context.resolveSingleTarget(getTargetKey()); 
		Actor attacker = (Actor) context.resolveSingleTarget(attackerReference);
		
		context.getLogic().fight(context.getPlayer(playerId), attacker, defender);
	}
	
	public EntityReference getAttackerReference() {
		return attackerReference;
	}

	@Override
	public boolean isSameActionGroup(GameAction anotherAction) {
		if (anotherAction.getActionType() != getActionType()) {
			return false;
		}
		PhysicalAttackAction physicalAttackAction = (PhysicalAttackAction) anotherAction;
		
		return this.getAttackerReference().equals(physicalAttackAction.getAttackerReference());
	}

	@Override
	public String toString() {
		return String.format("%s Attacker: %s Defender: %s", getActionType(), attackerReference, getTargetKey());
	}

}
