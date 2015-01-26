package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;

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
