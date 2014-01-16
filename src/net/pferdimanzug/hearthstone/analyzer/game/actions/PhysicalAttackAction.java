package net.pferdimanzug.hearthstone.analyzer.game.actions;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class PhysicalAttackAction extends GameAction {
	
	private EntityReference attackerReference;

	public PhysicalAttackAction(EntityReference attackerReference) {
		setTargetRequirement(TargetSelection.ENEMY_CHARACTERS);
		setActionType(ActionType.PHYSICAL_ATTACK);
		this.attackerReference = attackerReference;
	}

	@Override
	public void execute(GameContext context, int playerId) {
		Entity defender = context.resolveSingleTarget(playerId, getTargetKey()); 
		Entity attacker = context.resolveSingleTarget(playerId, attackerReference);
		context.getLogic().fight(attacker, defender);
	}


}
