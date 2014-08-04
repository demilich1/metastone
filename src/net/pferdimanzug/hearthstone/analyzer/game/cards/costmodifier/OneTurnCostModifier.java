package net.pferdimanzug.hearthstone.analyzer.game.cards.costmodifier;

import net.pferdimanzug.hearthstone.analyzer.game.cards.CardType;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.GameEventTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnStartTrigger;

public class OneTurnCostModifier extends CardCostModifier {
	
	private GameEventTrigger expirationTrigger = new TurnStartTrigger();

	public OneTurnCostModifier(CardType cardType, int manaModifier, boolean oneTime) {
		super(cardType, manaModifier, oneTime);
	}

	@Override
	public boolean interestedIn(GameEventType eventType) {
		return eventType == expirationTrigger.interestedIn();
	}

	@Override
	public void onGameEvent(GameEvent event) {
		Entity host = event.getGameContext().resolveSingleTarget(getHostReference());
		if (expirationTrigger.fire(event, host)) {
			expire();
		}
	}

}
