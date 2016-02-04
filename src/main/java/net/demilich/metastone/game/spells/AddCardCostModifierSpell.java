package net.demilich.metastone.game.spells;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
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
		logger.error(cardCostModifierDesc.toString());
		if (cardCostModifierDesc.contains(CardCostModifierArg.TARGET)) {
			Card card = (Card) context.resolveSingleTarget((EntityReference) cardCostModifierDesc.get(CardCostModifierArg.TARGET));
			cardCostModifierDesc = cardCostModifierDesc.removeArg(CardCostModifierArg.TARGET);
			cardCostModifierDesc = cardCostModifierDesc.addArg(CardCostModifierArg.ID, card.getId());
			logger.error(cardCostModifierDesc.toString());
		}
		CardCostModifier cardCostModifier = cardCostModifierDesc.create();
		cardCostModifier.setHost(target);
		context.addCardCostModifier(cardCostModifier);
	}

}
