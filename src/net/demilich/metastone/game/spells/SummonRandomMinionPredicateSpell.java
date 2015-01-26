package net.demilich.metastone.game.spells;

import java.util.function.Predicate;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.CardCollection;
import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class SummonRandomMinionPredicateSpell extends Spell {
	
	public static SpellDesc create(Predicate<Card> cardFilter) {
		SpellDesc desc = new SpellDesc(SummonRandomMinionPredicateSpell.class);
		desc.set(SpellArg.CARD_FILTER, cardFilter);
		desc.setTarget(EntityReference.NONE);
		return desc;
	}

	private static MinionCard getRandomMatchingMinionCard(Predicate<Card> cardFilter) {
		CardCollection allMinions = CardCatalogue.query(CardType.MINION);
		CardCollection relevantMinions = new CardCollection();
		for (Card card : allMinions) {
			if (cardFilter.test(card)) {
				relevantMinions.add(card);
			}
		}
		return (MinionCard) relevantMinions.getRandom();
	}
	
	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		@SuppressWarnings("unchecked")
		Predicate<Card> cardFilter = (Predicate<Card>) desc.get(SpellArg.CARD_FILTER);
		MinionCard minionCard = getRandomMatchingMinionCard(cardFilter);
		context.getLogic().summon(player.getId(), minionCard.summon());
	}

}
