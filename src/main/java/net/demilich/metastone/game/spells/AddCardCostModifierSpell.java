package net.demilich.metastone.game.spells;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.costmodifier.CardCostModifier;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.desc.manamodifier.CardCostModifierArg;
import net.demilich.metastone.game.spells.desc.manamodifier.CardCostModifierDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class AddCardCostModifierSpell extends Spell {
	
	Logger logger = LoggerFactory.getLogger(AddCardCostModifierSpell.class);

	public static SpellDesc create(EntityReference target, CardCostModifier cardCostModifier) {
		Map<SpellArg, Object> arguments = SpellDesc.build(AddCardCostModifierSpell.class);
		arguments.put(SpellArg.CARD_COST_MODIFIER, cardCostModifier);
		arguments.put(SpellArg.TARGET, target);
		return new SpellDesc(arguments);
	}

	public static SpellDesc create(CardCostModifier cardCostModifier) {
		return create(null, cardCostModifier);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		CardCostModifierDesc cardCostModifierDesc = (CardCostModifierDesc) desc.get(SpellArg.CARD_COST_MODIFIER);
		if (cardCostModifierDesc.contains(CardCostModifierArg.TARGET)) {
			// First, resolve the targets, so that you can get the current cards this affects.
			// This spell SPECIFICALLY targets cards, even if those cards would change. So,
			// targeting FRIENDLY_HAND would pull cards in the hand NOW, as opposed to cards
			// that will be added next turn. This is the difference between CardCostModifierSpell
			// and AddCardCostModifierSpell
			List<Entity> cards = context.resolveTarget(player, source, (EntityReference) cardCostModifierDesc.get(CardCostModifierArg.TARGET));
			List<Integer> cardIds = new ArrayList<Integer>();
			for (Entity card : cards) {
				logger.error(card.getName());
				cardIds.add(card.getId());
			}
			cardCostModifierDesc = cardCostModifierDesc.removeArg(CardCostModifierArg.TARGET);
			cardCostModifierDesc = cardCostModifierDesc.addArg(CardCostModifierArg.CARD_IDS, cardIds);
		}
		CardCostModifier cardCostModifier = cardCostModifierDesc.create();
		cardCostModifier.setHost(target);
		context.addCardCostModifier(cardCostModifier);
	}

}
