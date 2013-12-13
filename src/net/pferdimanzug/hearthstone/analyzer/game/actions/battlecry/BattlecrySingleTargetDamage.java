package net.pferdimanzug.hearthstone.analyzer.game.actions.battlecry;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetRequirement;
import net.pferdimanzug.hearthstone.analyzer.game.cards.EffectHint;

public class BattlecrySingleTargetDamage extends Battlecry {

	private final int damage;

	public BattlecrySingleTargetDamage(int damage) {
		this.damage = damage;
		setEffectHint(EffectHint.NEGATIVE);
		setTargetRequirement(TargetRequirement.ANY);
	}

	@Override
	public void execute(GameContext context, Player player) {
		context.getLogic().damage(getTarget(), damage);
	}
}
