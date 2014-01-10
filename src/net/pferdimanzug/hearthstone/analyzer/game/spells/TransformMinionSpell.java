package net.pferdimanzug.hearthstone.analyzer.game.spells;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class TransformMinionSpell implements ISpell {
	
	private static Logger logger = LoggerFactory.getLogger(TransformMinionSpell.class);
	
	private final MinionCard newMinion;

	public TransformMinionSpell(MinionCard newMinion) {
		this.newMinion = newMinion;
	}

	@Override
	public void cast(GameContext context, Player player, Entity target) {
		Player opponent = context.getOpponent(player);
		int index = player.getMinions().indexOf(target);
		List<Minion> minions = index == -1 ? opponent.getMinions() : player.getMinions();
		if (index == -1) {
			index = opponent.getMinions().indexOf(target);
		}
		
		Minion removedMinion = minions.remove(index);
		logger.debug("[{}] is transformed into a [{}]", removedMinion.getName(), newMinion.getName());
		minions.add(index, newMinion.summon());
	}

}
