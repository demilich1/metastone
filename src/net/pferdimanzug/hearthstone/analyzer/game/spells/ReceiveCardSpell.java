package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class ReceiveCardSpell extends Spell {
	
	private final Card card;

	public ReceiveCardSpell(Card card) {
		this.card = card;
	}

	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
		context.getLogic().receiveCard(player.getId(), card.clone());
	}

}
