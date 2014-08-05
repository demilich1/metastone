package net.pferdimanzug.hearthstone.analyzer.game.actions;

import net.pferdimanzug.hearthstone.analyzer.game.Environment;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.CardReference;

public abstract class PlayCardAction extends GameAction {

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
		context.getLogic().playCard(playerId, getCardReference());
		// card was countered, do not actually resolve its effects
		if (!card.hasTag(GameTag.COUNTERED)) {
			try {
				play(context, playerId);
			} catch (Exception e) {
				System.out.println("ERROR while playing card " + card + " id:" + card.getId());
				throw e;
			}

		}
		context.getLogic().afterCardPlayed(playerId, getCardReference());
		context.getEnvironment().remove(Environment.PENDING_CARD);
	}

	public CardReference getCardReference() {
		return cardReference;
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
