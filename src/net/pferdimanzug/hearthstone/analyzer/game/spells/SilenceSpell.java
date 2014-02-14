package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class SilenceSpell extends Spell {

	@Override
	protected void onCast(GameContext context, Player player, Actor target) {
		context.getLogic().silence((Minion) target);
	}

}
