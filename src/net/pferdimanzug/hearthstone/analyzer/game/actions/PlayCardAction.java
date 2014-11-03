package net.pferdimanzug.hearthstone.analyzer.game.actions;

import net.pferdimanzug.hearthstone.analyzer.game.Environment;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.CardReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class PlayCardAction extends GameAction {

	public static Logger logger = LoggerFactory.getLogger(PlayCardAction.class);

	private final CardReference cardReference;

	public PlayCardAction(CardReference cardReference) {
		this.cardReference = cardReference;
	}

	@Override
	public boolean canBeExecutedOn(GameContext context, Entity entity) {
		Card card = context.resolveCardReference(getCardReference());
		if (card instanceof SpellCard) {
			SpellCard spellCard = (SpellCard) card;
			return spellCard.canBeCastOn(entity);
		}

		return true;
	}

	@Override
	public void execute(GameContext context, int playerId) {
		Card card = context.resolveCardReference(getCardReference());
		context.getEnvironment().put(Environment.PENDING_CARD, card);

		try {
			context.getLogic().playCard(playerId, getCardReference());
			// card was countered, do not actually resolve its effects
			if (!card.hasStatus(GameTag.COUNTERED)) {
				play(context, playerId);
			}

		} catch (Exception e) {
			logger.error("ERROR while playing card " + card + " reference: " + cardReference);
			logger.error("Player1: " + context.getPlayer1().getName());
			logger.error("Player2: " + context.getPlayer2().getName());
			e.printStackTrace();
			System.exit(-1);
			throw e;
		}

		context.getLogic().afterCardPlayed(playerId, getCardReference());
		context.getEnvironment().remove(Environment.PENDING_CARD);
	}

	public CardReference getCardReference() {
		return cardReference;
	}

	@Override
	public String getPromptText() {
		return "[Play card]";
	}

	@Override
	public boolean isSameActionGroup(GameAction anotherAction) {
		if (anotherAction.getActionType() != getActionType()) {
			return false;
		}
		PlayCardAction playCardAction = (PlayCardAction) anotherAction;
		return this.cardReference.equals(playCardAction.cardReference);
	}

	protected abstract void play(GameContext context, int playerId);

	@Override
	public String toString() {
		return String.format("%s Card: %s Target: %s", getActionType(), cardReference, getTargetKey());
	}
}
