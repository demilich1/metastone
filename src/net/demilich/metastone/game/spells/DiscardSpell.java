package net.demilich.metastone.game.spells;

import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.EntityType;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class DiscardSpell extends Spell {

	public static final int ALL_CARDS = -1;

	public static SpellDesc create() {
		return create(1);
	}

	public static SpellDesc create(int numberOfCards) {
		Map<SpellArg, Object> arguments = SpellDesc.build(DiscardSpell.class);
		arguments.put(SpellArg.VALUE, numberOfCards);
		arguments.put(SpellArg.TARGET, EntityReference.NONE);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		if (target != null && target.getEntityType() == EntityType.CARD) {
			context.getLogic().discardCard(player, (Card)target);
			return;
		}
		
		int numberOfCards = desc.getInt(SpellArg.VALUE, 1);
		int cardCount = numberOfCards == ALL_CARDS ? player.getHand().getCount() : numberOfCards;

		for (int i = 0; i < cardCount; i++) {
			Card randomHandCard = player.getHand().getRandom();
			if (randomHandCard == null) {
				return;
			}
			context.getLogic().discardCard(player, randomHandCard);
		}
	}

}
