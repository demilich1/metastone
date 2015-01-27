package net.demilich.metastone.game.cards.costmodifier;

import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.spells.trigger.GameEventTrigger;
import net.demilich.metastone.game.spells.trigger.TurnStartTrigger;

public class OneTurnCostModifier extends CardCostModifier {

	private GameEventTrigger expirationTrigger;
	private GameEventTrigger turnStartTrigger = new TurnStartTrigger();

	public OneTurnCostModifier(CardType cardType, int manaModifier, GameEventTrigger expirationTrigger) {
		super(cardType, manaModifier);
		this.expirationTrigger = expirationTrigger;
	}

	public OneTurnCostModifier(CardType cardType, int manaModifier) {
		this(cardType, manaModifier, null);
	}

	@Override
	public OneTurnCostModifier clone() {
		OneTurnCostModifier clone = (OneTurnCostModifier) super.clone();
		clone.expirationTrigger = expirationTrigger != null ? (GameEventTrigger) expirationTrigger.clone() : null;
		clone.turnStartTrigger = (GameEventTrigger) turnStartTrigger.clone();
		return clone;
	}

	@Override
	public boolean interestedIn(GameEventType eventType) {
		if (turnStartTrigger.interestedIn() == eventType) {
			return true;
		}
		if (expirationTrigger == null) {
			return false;
		}
		return eventType == expirationTrigger.interestedIn() || expirationTrigger.interestedIn() == GameEventType.ALL;
	}

	@Override
	public void onGameEvent(GameEvent event) {
		Entity host = event.getGameContext().resolveSingleTarget(getHostReference());
		if (event.getEventType() == turnStartTrigger.interestedIn() && turnStartTrigger.fire(event, host)) {
			expire();
		} else if (expirationTrigger != null && expirationTrigger.interestedIn() == event.getEventType()
				&& expirationTrigger.fire(event, host)) {
			expire();
		}
	}

	@Override
	public void setOwner(int playerIndex) {
		super.setOwner(playerIndex);
		turnStartTrigger.setOwner(playerIndex);
		if (expirationTrigger != null) {
			expirationTrigger.setOwner(playerIndex);
		}
	}

}
