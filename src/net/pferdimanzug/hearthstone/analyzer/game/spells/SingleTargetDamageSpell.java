package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class SingleTargetDamageSpell extends DamageSpell {

	public SingleTargetDamageSpell(int damage) {
		super(damage);
	}

	@Override
	public void cast(GameContext context, Player player, Entity target) {
		context.getLogic().damage(target, getDamage());
	}

}
