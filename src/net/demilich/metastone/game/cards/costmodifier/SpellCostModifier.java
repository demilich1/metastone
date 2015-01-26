package net.demilich.metastone.game.cards.costmodifier;

import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;

public class SpellCostModifier extends CardCostModifier {

	public SpellCostModifier(int manaModifier, boolean oneTime) {
		super(CardType.SPELL, manaModifier, oneTime);
	}

	@Override
	public boolean interestedIn(GameEventType eventType) {
		return false;
	}

	@Override
	public void onGameEvent(GameEvent event) {
	}

}
