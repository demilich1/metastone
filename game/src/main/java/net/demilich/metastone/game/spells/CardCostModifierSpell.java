package net.demilich.metastone.game.spells;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.desc.filter.EntityFilter;
import net.demilich.metastone.game.spells.desc.manamodifier.CardCostModifierArg;
import net.demilich.metastone.game.spells.desc.manamodifier.CardCostModifierDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class CardCostModifierSpell extends Spell {

	public static SpellDesc create(CardCostModifierDesc cardCostModifierDesc) {
		return create(null, cardCostModifierDesc);
	}

	public static SpellDesc create(EntityReference target, CardCostModifierDesc cardCostModifierDesc) {
		Map<SpellArg, Object> arguments = SpellDesc.build(CardCostModifierSpell.class);
		arguments.put(SpellArg.CARD_COST_MODIFIER, cardCostModifierDesc);
		arguments.put(SpellArg.TARGET, target);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		CardCostModifierDesc manaModifierDesc = (CardCostModifierDesc) desc.get(SpellArg.CARD_COST_MODIFIER);
		EntityFilter cardFilter = (EntityFilter) desc.get(SpellArg.CARD_FILTER);
		if (manaModifierDesc.contains(CardCostModifierArg.TARGET)) {
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
			manaModifierDesc = manaModifierDesc.removeArg(CardCostModifierArg.TARGET);
			manaModifierDesc = manaModifierDesc.addArg(CardCostModifierArg.CARD_IDS, cardIds);
		}
		context.getLogic().addManaModifier(player, manaModifierDesc.create(), target);
	}

}
