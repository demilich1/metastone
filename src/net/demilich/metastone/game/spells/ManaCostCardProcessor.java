package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;

public class ManaCostCardProcessor implements ICardPostProcessor {
	
	private final int manaCostModifier;

	public ManaCostCardProcessor(int manaCostModifier) {
		this.manaCostModifier = manaCostModifier;
	}

	@Override
	public void processCard(GameContext context, Player player, Card card) {
		card.setAttribute(Attribute.MANA_COST_MODIFIER, manaCostModifier);
	}

}
