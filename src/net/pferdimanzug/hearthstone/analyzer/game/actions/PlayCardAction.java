package net.pferdimanzug.hearthstone.analyzer.game.actions;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public abstract class PlayCardAction extends GameAction {

	private final Card card;

	public PlayCardAction(Card card) {
		this.card = card;
	}

	@Override
	public boolean canBeExecutedOn(Entity entity) {
		if (card instanceof SpellCard) {
			SpellCard spellCard = (SpellCard) card;
			return spellCard.canBeCastOn(entity);
		}
		return true;
	}
	
	@Override
	public void execute(GameContext context, Player player) {
		context.getLogic().playCard(player, getCard());
		play(context, player);
	}

	public Card getCard() {
		return card;
	}

	protected abstract void play(GameContext context, Player player);
	
	

}
