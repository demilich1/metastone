package net.pferdimanzug.hearthstone.analyzer.game.actions;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class PhysicalAttackAction extends GameAction {
	
	private Entity attacker;

	public PhysicalAttackAction(Entity attacker) {
		setTargetRequirement(TargetSelection.ENEMY_CHARACTERS);
		setActionType(ActionType.PHYSICAL_ATTACK);
		this.attacker = attacker;
	}

	@Override
	public void execute(GameContext context, Player player) {
		context.getLogic().fight(attacker, getTarget());
	}


}
