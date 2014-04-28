package net.pferdimanzug.hearthstone.analyzer.game.spells.trigger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class TriggerManager implements Cloneable {
	private final HashMap<GameEventType, List<SpellTrigger>> triggers;

	public TriggerManager() {
		triggers = new HashMap<>();
	}

	private TriggerManager(TriggerManager otherTriggerManager) {
		triggers = new HashMap<>();
		for (GameEventType eventType : otherTriggerManager.triggers.keySet()) {
			triggers.put(eventType, new ArrayList<>(otherTriggerManager.triggers.get(eventType)));
		}
	}

	public void addTrigger(SpellTrigger trigger) {
		GameEventType eventType = trigger.interestedIn();
		if (!triggers.containsKey(eventType)) {
			triggers.put(eventType, new ArrayList<SpellTrigger>());
		}
		triggers.get(eventType).add(trigger);
	}

	@Override
	public TriggerManager clone() {
		return new TriggerManager(this);
	}

	public void fireGameEvent(GameEvent event) {
		if (!triggers.containsKey(event.getEventType())) {
			return;
		}
		for (SpellTrigger trigger : getListSnapshot(event.getEventType(), triggers)) {
			if (trigger.getLayer() != event.getTriggerLayer()) {
				continue;
			}
			// we need to double check here if the trigger still exists;
			// after all, a previous trigger may have removed it (i.e. double
			// corruption)
			if (triggers.get(event.getEventType()).contains(trigger)) {
				trigger.onGameEvent(event);
			}

			if (trigger.isExpired()) {
				triggers.get(event.getEventType()).remove(trigger);
			}
		}
	}

	private List<SpellTrigger> getListSnapshot(GameEventType eventType,
			HashMap<GameEventType, List<SpellTrigger>> triggerMap) {
		List<SpellTrigger> snapshot = new ArrayList<SpellTrigger>();
		snapshot.addAll(triggerMap.get(eventType));
		return snapshot;
	}

	public void removeTriggersAssociatedWith(EntityReference entityReference) {
		for (GameEventType gameEventType : GameEventType.values()) {
			if (!triggers.containsKey(gameEventType)) {
				continue;
			}
			for (SpellTrigger trigger : getListSnapshot(gameEventType, triggers)) {
				if (trigger.getHostReference().equals(entityReference)) {
					triggers.get(gameEventType).remove(trigger);
				}
			}
		}
	}

}
