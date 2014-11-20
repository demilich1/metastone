package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellArg;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class ReceiveRandomCardSpell extends Spell {
	
	public static SpellDesc create(TargetPlayer targetPlayer, Card... cards) {
		SpellDesc desc = new SpellDesc(ReceiveRandomCardSpell.class);
		desc.set(SpellArg.CARDS, cards);
		desc.setTarget(EntityReference.NONE);
		desc.setTargetPlayer(targetPlayer);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		Card[] cards = (Card[]) desc.get(SpellArg.CARDS);
		TargetPlayer targetPlayer = desc.getTargetPlayer();
		Player opponent = context.getOpponent(player);
		switch (targetPlayer) {
		case BOTH:
			receiveRandomCard(context, player, cards);
			receiveRandomCard(context, opponent, cards);
			break;
		case OPPONENT:
			receiveRandomCard(context, opponent, cards);
			break;
		case SELF:
			receiveRandomCard(context, player, cards);
			break;
		}
	}

	private void receiveRandomCard(GameContext context, Player player, Card[] cards) {
		Card randomCard = cards[context.getLogic().random(cards.length)];
		context.getLogic().receiveCard(player.getId(), randomCard.clone());
	}

}
