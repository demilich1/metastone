package net.demilich.metastone.game.spells.desc.filter;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;

public class InHandFilter extends EntityFilter {

	public InHandFilter(FilterDesc desc) {
		super(desc);
	}
	
	@Override
	protected boolean test(GameContext context, Player player, Entity entity) {
		Card card = null;
		if (entity instanceof Card) {
			card = (Card) entity;
		} else if (entity instanceof Actor) {
			Actor actor = (Actor) entity;
			card = actor.getSourceCard();
		} else {
			return false;
		}

		return player.getHand().containsCard(card);
	}

}
