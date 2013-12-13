package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Minion;

public class ReturnMinionToHandSpell implements ISpell {

	@Override
	public void cast(GameContext context, Player player, Entity target) {
		Minion minion = (Minion) target;
		player.getMinions().remove(minion);
		player.getHand().add(minion.getSourceCard());
	}

}
