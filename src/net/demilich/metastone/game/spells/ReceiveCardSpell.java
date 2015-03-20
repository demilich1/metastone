package net.demilich.metastone.game.spells;

import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class ReceiveCardSpell extends Spell {

	public static SpellDesc create(Card card) {
		return create(TargetPlayer.SELF, card);
	}

	public static SpellDesc create(TargetPlayer targetPlayer, Card... cards) {
		Map<SpellArg, Object> arguments = SpellDesc.build(ReceiveCardSpell.class);
		arguments.put(SpellArg.CARD, cards);
		arguments.put(SpellArg.TARGET_PLAYER, targetPlayer);
		arguments.put(SpellArg.TARGET, EntityReference.NONE);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		Card[] cards = (Card[]) desc.get(SpellArg.CARD);
		for (Card card : cards) {
			context.getLogic().receiveCard(player.getId(), card.clone());
		}

	}

}
