package net.demilich.metastone.game.cards.costmodifier;

import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.spells.trigger.GameEventTrigger;
import net.demilich.metastone.game.spells.trigger.TurnStartTrigger;

public class OneTurnCostModifier extends CardCostModifier {
	
	private GameEventTrigger expirationTrigger = new TurnStartTrigger();

	public OneTurnCostModifier(CardType cardType, int manaModifier, boolean oneTime) {
		super(cardType, manaModifier, oneTime);
	}
	
	@Override
	public OneTurnCostModifier clone() {
		OneTurnCostModifier clone = (OneTurnCostModifier) super.clone();
		clone.expirationTrigger = (GameEventTrigger) expirationTrigger.clone();
		return clone;
	}

	@Override
	public boolean interestedIn(GameEventType eventType) {
		return eventType == expirationTrigger.interestedIn() || expirationTrigger.interestedIn() == GameEventType.ALL;
	}
	
	@Override
	public void onGameEvent(GameEvent event) {
		Entity host = event.getGameContext().resolveSingleTarget(getHostReference());
		if (expirationTrigger.fire(event, host)) {
			expire();
		}
	}

	@Override
	public void setOwner(int playerIndex) {
		super.setOwner(playerIndex);
		expirationTrigger.setOwner(playerIndex);
	}

}
