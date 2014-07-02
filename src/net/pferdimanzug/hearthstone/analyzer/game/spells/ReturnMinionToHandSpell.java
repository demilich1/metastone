package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReturnMinionToHandSpell extends Spell {

	private static Logger logger = LoggerFactory.getLogger(ReturnMinionToHandSpell.class);

	private final int manaCostModifier;

	public ReturnMinionToHandSpell() {
		this(0);
	}

	public ReturnMinionToHandSpell(int manaCostModifier) {
		this.manaCostModifier = manaCostModifier;
	}

	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
		Minion minion = (Minion) target;
		Player owner = context.getPlayer(minion.getOwner());
		logger.debug("{} is returned to {}'s hand", minion, owner.getName());
		context.getLogic().removeMinion(minion);
		Card sourceCard = minion.returnToHand();
		context.getLogic().receiveCard(minion.getOwner(), sourceCard);
		sourceCard.setTag(GameTag.MANA_COST_MODIFIER, manaCostModifier);
	}

}
