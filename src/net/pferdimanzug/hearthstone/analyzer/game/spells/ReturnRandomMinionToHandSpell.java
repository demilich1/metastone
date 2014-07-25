package net.pferdimanzug.hearthstone.analyzer.game.spells;

import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class ReturnRandomMinionToHandSpell extends ReturnMinionToHandSpell {

	@Override
	public void cast(GameContext context, Player player, List<Entity> targets) {
		if (targets == null || targets.isEmpty()) {
			return;
		}
		
		onCast(context, player, SpellUtils.getRandomTarget(targets));
	}
	
	

}
