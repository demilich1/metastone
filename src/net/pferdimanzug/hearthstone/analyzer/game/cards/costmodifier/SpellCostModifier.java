package net.pferdimanzug.hearthstone.analyzer.game.cards.costmodifier;

import net.pferdimanzug.hearthstone.analyzer.game.cards.CardType;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;

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
