package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;

public class ManaCostCardProcessor implements ICardPostProcessor {
	
	private final int manaCostModifier;

	public ManaCostCardProcessor(int manaCostModifier) {
		this.manaCostModifier = manaCostModifier;
	}

	@Override
	public void processCard(GameContext context, Player player, Card card) {
		card.setTag(GameTag.MANA_COST_MODIFIER, manaCostModifier);
	}

}
