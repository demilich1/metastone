package net.demilich.metastone.game.spells;

import java.util.Map;
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
		return create(TargetPlayer.SELF, cardFilter);
	}

	public static SpellDesc create(TargetPlayer targetPlayer, Predicate<Card> cardFilter) {
		Map<SpellArg, Object> arguments = SpellDesc.build(SummonRandomMinionPredicateSpell.class);
		arguments.put(SpellArg.CARD_FILTER, cardFilter);
		arguments.put(SpellArg.TARGET, EntityReference.NONE);
		arguments.put(SpellArg.TARGET_PLAYER, targetPlayer);
		return new SpellDesc(arguments);
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
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		@SuppressWarnings("unchecked")
		Predicate<Card> cardFilter = (Predicate<Card>) desc.get(SpellArg.CARD_FILTER);
		int boardPosition = desc.contains(SpellArg.BOARD_POSITION_ABSOLUTE) ? desc.getInt(SpellArg.BOARD_POSITION_ABSOLUTE) : -1;
		MinionCard minionCard = getRandomMatchingMinionCard(cardFilter);
		context.getLogic().summon(player.getId(), minionCard.summon(), null, boardPosition, false);
	}

}
