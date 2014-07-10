package net.pferdimanzug.hearthstone.analyzer.game.spells;

import java.util.concurrent.ThreadLocalRandom;

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

	private Card getRandomCard() {
		int randomIndex = ThreadLocalRandom.current().nextInt(cards.length);
		return cards[randomIndex];
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
		Card randomCard = getRandomCard();
		context.getLogic().receiveCard(player.getId(), randomCard.clone());
	}

}
