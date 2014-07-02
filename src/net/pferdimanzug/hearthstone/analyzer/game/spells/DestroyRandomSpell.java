package net.pferdimanzug.hearthstone.analyzer.game.spells;

import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class DestroyRandomSpell extends DestroySpell {
	
	@Override
	public void cast(GameContext context, Player player, List<Entity> targets) {
		Entity randomTarget = getRandomTarget(targets);
		onCast(context, player, randomTarget);
	}

}
