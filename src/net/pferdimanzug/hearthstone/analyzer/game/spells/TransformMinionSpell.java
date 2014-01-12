package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransformMinionSpell extends Spell {
	
	private static Logger logger = LoggerFactory.getLogger(TransformMinionSpell.class);
	
	private final MinionCard newMinion;

	public TransformMinionSpell(MinionCard newMinion) {
		this.newMinion = newMinion;
	}

	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
		/*
		Player opponent = context.getOpponent(player);
		int index = player.getMinions().indexOf(target);
		List<Minion> minions = index == -1 ? opponent.getMinions() : player.getMinions();
		if (index == -1) {
			index = opponent.getMinions().indexOf(target);
		}
		minions.add(index, newMinion.summon());
		*/
		
		logger.debug("{} is transformed into a {}", target, newMinion);
		// replace bycontext.getLogic().remove(target) if problems like incorrect triggering of stuff happens
		context.getLogic().destroy(target);
		context.getLogic().summon(player, newMinion.summon(), null);

	}

}
