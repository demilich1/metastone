package net.pferdimanzug.hearthstone.analyzer.game.actions;

import net.pferdimanzug.hearthstone.analyzer.game.Environment;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardType;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.CardLocation;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.CardReference;

public abstract class PlayCardAction extends GameAction {

	private final Card card;

	public PlayCardAction(Card card) {
		this.card = card;
	}

	@Override
	public boolean canBeExecutedOn(Entity entity) {
		if (card.getCardType() == CardType.SPELL) {
			SpellCard spellCard = (SpellCard) card;
			return spellCard.canBeCastOn(entity);
		}
		return true;
	}

	@Override
	public void execute(GameContext context, int playerId) {
		CardReference cardReference = new CardReference(playerId, CardLocation.HAND, card.getId());
		context.getEnvironment().put(Environment.PENDING_CARD, card);
		context.getLogic().playCard(playerId, cardReference);
		// card was countered, do not actually resolve its effects
		if (!card.hasTag(GameTag.COUNTERED)) {
			try {
				play(context, playerId);
			} catch (Exception e) {
				System.out.println("ERROR while playing card " + card + " id:" + card.getId());
				throw e;
			}

		}
		context.getLogic().afterCardPlayed(playerId, cardReference);
		context.getEnvironment().remove(Environment.PENDING_CARD);
	}

	public Card getCard() {
		return card;
	}

	@Override
	public boolean isSameActionGroup(GameAction anotherAction) {
		if (anotherAction.getActionType() != getActionType()) {
			return false;
		}
		PlayCardAction playCardAction = (PlayCardAction) anotherAction;
		return this.getCard() == playCardAction.getCard();
	}

	protected abstract void play(GameContext context, int playerId);

	@Override
	public String toString() {
		return String.format("%s Card: %s Target: %s", getActionType(), card, getTargetKey());
	}

}
