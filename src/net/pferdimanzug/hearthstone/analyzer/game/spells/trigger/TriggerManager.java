package net.pferdimanzug.hearthstone.analyzer.game.spells.trigger;

import java.util.ArrayList;
import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.utils.IDisposable;

public class TriggerManager implements Cloneable, IDisposable {
	private final List<IGameEventListener> triggers = new ArrayList<IGameEventListener>();;

	public TriggerManager() {
	}

	private TriggerManager(TriggerManager otherTriggerManager) {
		for (IGameEventListener gameEventListener : otherTriggerManager.triggers) {
			triggers.add(gameEventListener.clone());
		}
	}

	public void addTrigger(IGameEventListener trigger) {
		triggers.add(trigger);
		if (triggers.size() > 100) {
			System.out.println("Warning, many triggers: " + triggers.size() + " adding one of type: " + trigger);
		}
	}
	
	public void removeTrigger(IGameEventListener trigger) {
		if (!triggers.remove(trigger)) {
			System.out.println("Failed to remove trigger " + trigger);
		}
	}

	@Override
	public TriggerManager clone() {
		return new TriggerManager(this);
	}

	@Override
	public void dispose() {
		triggers.clear();
	}

	public void fireGameEvent(GameEvent event) {
		for (IGameEventListener trigger : getListSnapshot(triggers)) {
			if (trigger.getLayer() != event.getTriggerLayer()) {
				continue;
			}

			if (!trigger.interestedIn(event.getEventType())) {
				continue;
			}
			// we need to double check here if the trigger still exists;
			// after all, a previous trigger may have removed it (i.e. double
			// corruption)
			if (triggers.contains(trigger)) {
				trigger.onGameEvent(event);
			}

			if (trigger.isExpired()) {
				triggers.remove(trigger);
			}
		}
	}

	private List<IGameEventListener> getListSnapshot(List<IGameEventListener> triggerList) {
		return new ArrayList<IGameEventListener>(triggerList);
	}

	public List<IGameEventListener> getTriggersAssociatedWith(EntityReference entityReference) {
		List<IGameEventListener> relevantTriggers = new ArrayList<>();
		for (IGameEventListener trigger : triggers) {
			if (trigger.getHostReference().equals(entityReference)) {
				relevantTriggers.add(trigger);
			}
		}
		return relevantTriggers;
	}

	public void removeTriggersAssociatedWith(EntityReference entityReference) {
		for (IGameEventListener trigger : getListSnapshot(triggers)) {
			if (trigger.getHostReference().equals(entityReference)) {
				triggers.remove(trigger);
			}
		}
	}

}
