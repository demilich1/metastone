package net.pferdimanzug.hearthstone.analyzer.game.cards.costmodifier;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardType;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.GameEventTrigger;

public class ToggleCostModifier extends CardCostModifier {

	private GameEventTrigger toggleOnTrigger;
	private GameEventTrigger toggleOffTrigger;
	private boolean ready;

	public ToggleCostModifier(CardType cardType, int manaModifier, boolean oneTime) {
		super(cardType, manaModifier, oneTime);
	}

	@Override
	protected boolean appliesTo(Card card) {
		if (!ready) {
			return false;
		}
		return super.appliesTo(card);
	}
	
	@Override
	public CardCostModifier clone() {
		ToggleCostModifier clone = (ToggleCostModifier) super.clone();
		clone.toggleOnTrigger = toggleOnTrigger.clone();
		clone.toggleOffTrigger = toggleOffTrigger.clone();
		return clone;
	}

	@Override
	public boolean interestedIn(GameEventType eventType) {
		return eventType == toggleOnTrigger.interestedIn() || eventType == toggleOffTrigger.interestedIn();
	}

	@Override
	public void onGameEvent(GameEvent event) {
		Entity host = event.getGameContext().resolveSingleTarget(getHostReference());
		if (toggleOnTrigger.interestedIn() == event.getEventType() && toggleOnTrigger.fire(event, host)) {
			ready = true;
		} else if (toggleOffTrigger.interestedIn() == event.getEventType() && toggleOffTrigger.fire(event, host)) {
			ready = false;
		}

	}

	public void setToggleOffTrigger(GameEventTrigger toggleOffTrigger) {
		this.toggleOffTrigger = toggleOffTrigger;
	}

	public void setToggleOnTrigger(GameEventTrigger toggleOnTrigger) {
		this.toggleOnTrigger = toggleOnTrigger;
	}

}
