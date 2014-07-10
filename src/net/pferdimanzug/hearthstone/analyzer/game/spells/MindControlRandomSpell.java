package net.pferdimanzug.hearthstone.analyzer.game.spells;

import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class MindControlRandomSpell extends MindControlSpell {
	
	@Override
	public void cast(GameContext context, Player player, List<Entity> targets) {
		if (targets == null || targets.isEmpty()) {
			return;
		}
		int randomIndex = context.getLogic().random(targets.size());
		onCast(context, player, targets.get(randomIndex));
	}

}
