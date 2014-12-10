package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellArg;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

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
