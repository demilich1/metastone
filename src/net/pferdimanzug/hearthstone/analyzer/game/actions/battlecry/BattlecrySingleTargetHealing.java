package net.pferdimanzug.hearthstone.analyzer.game.actions.battlecry;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetRequirement;
import net.pferdimanzug.hearthstone.analyzer.game.cards.EffectHint;

public class BattlecrySingleTargetHealing extends Battlecry {

	private final int healing;

	public BattlecrySingleTargetHealing(int healing) {
		this.healing = healing;
		setTargetRequirement(TargetRequirement.ANY);
		setEffectHint(EffectHint.POSITIVE);
	}

	@Override
	public void execute(GameContext context, Player player) {
		context.getLogic().heal(getTarget(), healing);
	}

}
