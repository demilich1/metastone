package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.costmodifier.CardCostModifier;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellArg;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

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
