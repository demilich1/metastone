package net.pferdimanzug.hearthstone.analyzer.game.spells;

import java.util.function.Predicate;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCatalogue;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCollection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardType;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellArg;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class SummonRandomMinionPredicateSpell extends Spell {
	
	public static SpellDesc create(Predicate<Card> cardFilter) {
		SpellDesc desc = new SpellDesc(SummonRandomMinionPredicateSpell.class);
		desc.set(SpellArg.CARD_FILTER, cardFilter);
		desc.setTarget(EntityReference.NONE);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		@SuppressWarnings("unchecked")
		Predicate<Card> cardFilter = (Predicate<Card>) desc.get(SpellArg.CARD_FILTER);
		MinionCard minionCard = getRandomMatchingMinionCard(cardFilter);
		context.getLogic().summon(player.getId(), minionCard.summon(), null, null, false);
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

}
