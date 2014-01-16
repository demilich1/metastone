package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReturnMinionToHandSpell extends Spell {

	private static Logger logger = LoggerFactory.getLogger(ReturnMinionToHandSpell.class);

	@Override
	protected void onCast(GameContext context, Player player, Entity minion) {
		logger.debug("{} is returned to {}'s hand", minion, player.getName());
		player.getMinions().remove(minion);
		Card sourceCard = minion.getSourceCard();
		context.getPendingCards().add(sourceCard);
		context.getLogic().receiveCard(minion.getOwner(), sourceCard);
		context.getPendingCards().remove(sourceCard);
	}

}
