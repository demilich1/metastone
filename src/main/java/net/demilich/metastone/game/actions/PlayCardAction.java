package net.demilich.metastone.game.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.targeting.CardReference;

public abstract class PlayCardAction extends GameAction {

	public static Logger logger = LoggerFactory.getLogger(PlayCardAction.class);

	private final CardReference cardReference;
	private int groupIndex;

	public PlayCardAction(CardReference cardReference) {
		this.cardReference = cardReference;
	}

	@Override
	public boolean canBeExecutedOn(GameContext context, Player player, Entity entity) {
		Card card = context.resolveCardReference(getCardReference());
		if (card instanceof SpellCard) {
			SpellCard spellCard = (SpellCard) card;
			return spellCard.canBeCastOn(context, player, entity);
		}

		return true;
	}

	@Override
	public void execute(GameContext context, int playerId) {
		Card card = context.resolveCardReference(getCardReference());
		context.setPendingCard(card);
		try {
			context.getLogic().playCard(playerId, getCardReference());
			// card was countered, do not actually resolve its effects
			if (!card.hasAttribute(Attribute.COUNTERED)) {
				play(context, playerId);
			}

		} catch (Exception e) {
			logger.error("ERROR while playing card " + card + " reference: " + cardReference);
			context.getLogic().panicDump();
			e.printStackTrace();
			System.exit(-1);
			throw e;
		}

		context.getLogic().afterCardPlayed(playerId, getCardReference());
		context.setPendingCard(null);
	}

	public CardReference getCardReference() {
		return cardReference;
	}

	public int getGroupIndex() {
		return groupIndex;
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
		return playCardAction.getGroupIndex() == getGroupIndex() && this.cardReference.equals(playCardAction.cardReference);
	}

	protected abstract void play(GameContext context, int playerId);

	public void setGroupIndex(int groupIndex) {
		this.groupIndex = groupIndex;
	}

	@Override
	public String toString() {
		return String.format("%s Card: %s Target: %s", getActionType(), cardReference, getTargetKey());
	}
}
