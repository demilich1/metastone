package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class AreaFreezeSpell extends AreaSpell {

	private final int damage;

	public AreaFreezeSpell(int damage) {
		super(TargetSelection.ENEMY_MINIONS);
		this.damage = damage;
	}

	@Override
	protected void forEachTarget(GameContext context, Player player, Entity entity) {
		entity.setTag(GameTag.FROZEN);
		if (damage > 0) {
			context.getLogic().damage(entity, damage);
		}
	}

	

}
