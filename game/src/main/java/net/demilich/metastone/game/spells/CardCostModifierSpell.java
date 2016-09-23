package net.demilich.metastone.game.spells;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.costmodifier.CardCostModifier;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.desc.filter.EntityFilter;
import net.demilich.metastone.game.spells.desc.manamodifier.CardCostModifierArg;
import net.demilich.metastone.game.spells.desc.manamodifier.CardCostModifierDesc;
import net.demilich.metastone.game.spells.desc.valueprovider.AlgebraicOperation;
import net.demilich.metastone.game.targeting.EntityReference;

public class CardCostModifierSpell extends Spell {

	public static SpellDesc create(CardCostModifierDesc cardCostModifierDesc) {
		return create(cardCostModifierDesc, null);
	}

	public static SpellDesc create(CardCostModifierDesc cardCostModifierDesc, EntityFilter cardFilter) {
		Map<SpellArg, Object> arguments = SpellDesc.build(CardCostModifierSpell.class);
		arguments.put(SpellArg.CARD_COST_MODIFIER, cardCostModifierDesc);
		arguments.put(SpellArg.CARD_FILTER, cardFilter);
		return new SpellDesc(arguments);
	}

	public static SpellDesc create(EntityReference target, AlgebraicOperation operation, int value) {
		return CardCostModifierSpell.create(target, operation, value, null);
	}


	public static SpellDesc create(EntityReference target, AlgebraicOperation operation, int value, EntityFilter cardFilter) {
		CardCostModifierDesc manaModifierDesc = new CardCostModifierDesc(CardCostModifierDesc.build(CardCostModifier.class));
		manaModifierDesc = manaModifierDesc.addArg(CardCostModifierArg.OPERATION, operation);
		manaModifierDesc = manaModifierDesc.addArg(CardCostModifierArg.TARGET, target);
		manaModifierDesc = manaModifierDesc.addArg(CardCostModifierArg.VALUE, value);
		return CardCostModifierSpell.create(manaModifierDesc, cardFilter);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		CardCostModifierDesc manaModifierDesc = (CardCostModifierDesc) desc.get(SpellArg.CARD_COST_MODIFIER);
		EntityFilter cardFilter = (EntityFilter) desc.get(SpellArg.CARD_FILTER);
		if (manaModifierDesc.get(CardCostModifierArg.TARGET) != null) {
			// First, resolve the targets, so that you can get the current cards this affects.
			// This spell SPECIFICALLY targets cards, even if those cards would change. So,
			// targeting FRIENDLY_HAND would pull cards in the hand NOW, as opposed to cards
			// that will be added next turn.
			List<Entity> cards = context.resolveTarget(player, source, (EntityReference) manaModifierDesc.get(CardCostModifierArg.TARGET));
			List<Integer> cardIds = new ArrayList<Integer>();
			for (Entity card : cards) {
				if (cardFilter == null || cardFilter.matches(context, player, card)) {
					cardIds.add(card.getId());
				}
			}
			
			if (cardIds.isEmpty()) {
				return;
			}
			manaModifierDesc = manaModifierDesc.removeArg(CardCostModifierArg.TARGET);
			manaModifierDesc = manaModifierDesc.addArg(CardCostModifierArg.CARD_IDS, cardIds);
		} else if (target != null && target instanceof Card) {
			List<Integer> cardIds = new ArrayList<Integer>();
			cardIds.add(target.getId());
			manaModifierDesc = manaModifierDesc.addArg(CardCostModifierArg.CARD_IDS, cardIds);
		}
		if (target == null) {
			target = player;
		}
		context.getLogic().addManaModifier(player, manaModifierDesc.create(), target);
	}

}
