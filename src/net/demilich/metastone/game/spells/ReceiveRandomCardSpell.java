package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

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
		Card randomCard = cards[context.getLogic().random(cards.length)];
		context.getLogic().receiveCard(player.getId(), randomCard.clone());
	}

}
