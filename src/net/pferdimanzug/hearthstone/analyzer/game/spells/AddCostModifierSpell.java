package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.costmodifier.CardCostModifier;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class AddCostModifierSpell extends Spell {
	
	private final CardCostModifier costModifier;

	public AddCostModifierSpell(CardCostModifier costModifier) {
		this.costModifier = costModifier;
	}

	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
		context.getLogic().addManaModifier(player, costModifier, target);
	}

}
