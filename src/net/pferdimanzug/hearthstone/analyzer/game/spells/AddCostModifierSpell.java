package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.costmodifier.CardCostModifier;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class AddCostModifierSpell extends Spell {
	
	public AddCostModifierSpell(CardCostModifier costModifier) {
		setCloneableData(costModifier);
	}

	public CardCostModifier getCostModifier() {
		return (CardCostModifier) getCloneableData()[0];
	}

	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
		context.getLogic().addManaModifier(player, getCostModifier(), target);
	}

}
