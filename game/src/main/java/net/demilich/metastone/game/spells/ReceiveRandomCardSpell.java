package net.demilich.metastone.game.spells;

import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class ReceiveRandomCardSpell extends Spell {

	public static SpellDesc create(TargetPlayer targetPlayer, Card... cards) {
		Map<SpellArg, Object> arguments = SpellDesc.build(ReceiveRandomCardSpell.class);
		arguments.put(SpellArg.CARDS, cards);
		arguments.put(SpellArg.TARGET_PLAYER, targetPlayer);
		arguments.put(SpellArg.TARGET, EntityReference.NONE);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		Card[] cards = SpellUtils.getCards(context, desc);
		Card randomCard = cards[context.getLogic().random(cards.length)];
		context.getLogic().receiveCard(player.getId(), randomCard);
	}

}
