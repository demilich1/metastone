package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.costmodifier.CardCostModifier;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class AddCostModifierSpell extends Spell {
	
	public static SpellDesc create(CardCostModifier cardCostModifier) {
		SpellDesc desc = new SpellDesc(AddCostModifierSpell.class);
		desc.set(SpellArg.CARD_COST_MODIFIER, cardCostModifier);
		return desc;
	}
	
	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		CardCostModifier cardCostModifier = (CardCostModifier) desc.get(SpellArg.CARD_COST_MODIFIER);
		context.getLogic().addManaModifier(player, cardCostModifier, target);
	}

}
