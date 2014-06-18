package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class NullSpell extends Spell {

	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
		// do nothing at all
	}

}
