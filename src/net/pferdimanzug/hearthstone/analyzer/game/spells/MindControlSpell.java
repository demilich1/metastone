package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class MindControlSpell implements ISpell {

	@Override
	public void cast(GameContext context, Player player, Entity target) {
		Minion minion = (Minion) target;
		context.getOpponent(player).getMinions().remove(minion);
		player.getMinions().add(minion);
		minion.setOwner(player);
	}

}
