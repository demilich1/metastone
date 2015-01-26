package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class DiscardCardSpell extends Spell {

	public static SpellDesc create() {
		return create(1);
	}

	public static SpellDesc create(int numberOfCards) {
		SpellDesc desc = new SpellDesc(DiscardCardSpell.class);
		desc.set(SpellArg.NUMBER_OF_CARDS, numberOfCards);
		desc.setTarget(EntityReference.NONE);
		return desc;
	}

	public static final int ALL_CARDS = -1;

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		int numberOfCards = desc.getInt(SpellArg.NUMBER_OF_CARDS);
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
