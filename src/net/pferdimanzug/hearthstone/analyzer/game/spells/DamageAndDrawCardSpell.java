package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class DamageAndDrawCardSpell extends SingleTargetDamageSpell {

	public DamageAndDrawCardSpell(int damage) {
		super(damage);
	}

	@Override
	public void cast(GameContext context, Player player, Entity target) {
		super.cast(context, player, target);
		context.getLogic().drawCard(player);
	}
}
