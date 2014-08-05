package net.pferdimanzug.hearthstone.analyzer.game.spells;

import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class ReceiveRandomCardSpell extends Spell {

	private final TargetPlayer targetPlayer;

	public ReceiveRandomCardSpell(TargetPlayer targetPlayer, Card... cards) {
		this.targetPlayer = targetPlayer;
		setCloneableData(cards);
	}

	public List<Card> getCards() {
		return getCloneableDataCollection();
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
		List<Card> cards = getCards();
		Card randomCard = cards.get(context.getLogic().random(getCards().size()));
		context.getLogic().receiveCard(player.getId(), randomCard.clone());
	}

}
