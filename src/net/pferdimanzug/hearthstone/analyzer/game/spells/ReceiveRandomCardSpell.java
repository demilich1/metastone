package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class ReceiveRandomCardSpell extends Spell {

	private final TargetPlayer targetPlayer;
	private final Card[] cards;

	public ReceiveRandomCardSpell(TargetPlayer targetPlayer, Card... cards) {
		this.targetPlayer = targetPlayer;
		this.cards = cards;
	}

	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
		Player opponent = context.getOpponent(player);
		switch (targetPlayer) {
		case BOTH:
			receiveRandomCard(context, player);
			receiveRandomCard(context, opponent);
			break;
		case OPPONENT:
			receiveRandomCard(context, opponent);
			break;
		case SELF:
			receiveRandomCard(context, player);
			break;
		}
	}

	private void receiveRandomCard(GameContext context, Player player) {
		Card randomCard = cards[context.getLogic().random(cards.length)];
		context.getLogic().receiveCard(player.getId(), randomCard.clone());
	}

}
