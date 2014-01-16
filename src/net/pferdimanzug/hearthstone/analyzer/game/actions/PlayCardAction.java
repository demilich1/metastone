package net.pferdimanzug.hearthstone.analyzer.game.actions;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
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
		context.getLogic().playCard(playerId, cardReference);
		play(context, playerId);
	}

	public Card getCard() {
		return card;
	}

	protected abstract void play(GameContext context, int playerId);
	
	

}
