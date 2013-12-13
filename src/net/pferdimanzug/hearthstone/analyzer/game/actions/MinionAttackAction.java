package net.pferdimanzug.hearthstone.analyzer.game.actions;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.EffectHint;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class MinionAttackAction extends GameAction {
	
	private Entity attacker;

	public MinionAttackAction(Entity attacker) {
		setTargetRequirement(TargetRequirement.ENEMY_CHARACTERS);
		setActionType(ActionType.PHYSICAL_ATTACK);
		setEffectHint(EffectHint.NEGATIVE);
		this.attacker = attacker;
	}

	@Override
	public void execute(GameContext context, Player player) {
		context.getLogic().fight(attacker, getTarget());
	}


}
