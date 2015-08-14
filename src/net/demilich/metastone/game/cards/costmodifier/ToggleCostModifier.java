package net.demilich.metastone.game.cards.costmodifier;

import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.spells.desc.manamodifier.CardCostModifierArg;
import net.demilich.metastone.game.spells.desc.manamodifier.CardCostModifierDesc;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerDesc;
import net.demilich.metastone.game.spells.trigger.GameEventTrigger;

public class ToggleCostModifier extends CardCostModifier {

	private GameEventTrigger toggleOnTrigger;
	private GameEventTrigger toggleOffTrigger;
	private boolean ready;

	public ToggleCostModifier(CardCostModifierDesc desc) {
		super(desc);
		EventTriggerDesc triggerDesc = (EventTriggerDesc) desc.get(CardCostModifierArg.TOGGLE_ON_TRIGGER);
		this.toggleOnTrigger = triggerDesc.create();

		triggerDesc = (EventTriggerDesc) desc.get(CardCostModifierArg.TOGGLE_OFF_TRIGGER);
		this.toggleOffTrigger = triggerDesc.create();
	}

	@Override
	public boolean appliesTo(Card card) {
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
		if (toggleOnTrigger.interestedIn() == event.getEventType() && toggleOnTrigger.fires(event, host)) {
			ready = true;
		} else if (toggleOffTrigger.interestedIn() == event.getEventType() && toggleOffTrigger.fires(event, host)) {
			ready = false;
		}
	}

	@Override
	public void setOwner(int playerIndex) {
		super.setOwner(playerIndex);
		toggleOnTrigger.setOwner(playerIndex);
		toggleOffTrigger.setOwner(playerIndex);
	}

}
