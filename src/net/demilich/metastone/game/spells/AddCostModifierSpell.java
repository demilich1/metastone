package net.demilich.metastone.game.spells;

import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.costmodifier.CardCostModifier;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class AddCostModifierSpell extends Spell {
	
	public static SpellDesc create(CardCostModifier cardCostModifier) {
		return create(null, cardCostModifier);
	}
	
	public static SpellDesc create(EntityReference target, CardCostModifier cardCostModifier) {
		Map<SpellArg, Object> arguments = SpellDesc.build(AddCostModifierSpell.class);
		arguments.put(SpellArg.CARD_COST_MODIFIER, cardCostModifier);
		arguments.put(SpellArg.TARGET, target);
		return new SpellDesc(arguments);
	}
	
	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		CardCostModifier cardCostModifier = (CardCostModifier) desc.get(SpellArg.CARD_COST_MODIFIER);
		context.getLogic().addManaModifier(player, cardCostModifier, target);
	}

}
