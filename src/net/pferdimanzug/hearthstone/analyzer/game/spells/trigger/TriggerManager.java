package net.pferdimanzug.hearthstone.analyzer.game.spells.trigger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;
import net.pferdimanzug.hearthstone.analyzer.game.events.IGameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class TriggerManager implements Cloneable {
	private final HashMap<GameEventType, List<SpellTrigger>> triggers;
	private final HashMap<GameEventType, List<SpellTrigger>> secrets;

	public TriggerManager() {
		triggers = new HashMap<>();
		secrets = new HashMap<>();
	}

	private TriggerManager(TriggerManager otherTriggerManager) {
		triggers = new HashMap<>();
		for (GameEventType eventType : otherTriggerManager.triggers.keySet()) {
			triggers.put(eventType, new ArrayList<>(otherTriggerManager.triggers.get(eventType)));
		}
		secrets = new HashMap<>();
		for (GameEventType eventType : otherTriggerManager.secrets.keySet()) {
			secrets.put(eventType, new ArrayList<>(otherTriggerManager.secrets.get(eventType)));
		}
	}

	public void addTrigger(SpellTrigger trigger) {
		GameEventType eventType = trigger.interestedIn();
		if (!triggers.containsKey(eventType)) {
			triggers.put(eventType, new ArrayList<SpellTrigger>());
		}
		triggers.get(eventType).add(trigger);
	}

	public void addSecret(SpellTrigger secret) {
		GameEventType eventType = secret.interestedIn();
		if (!secrets.containsKey(eventType)) {
			secrets.put(eventType, new ArrayList<SpellTrigger>());
		}
		secrets.get(eventType).add(secret);
	}

	@Override
	public TriggerManager clone() {
		return new TriggerManager(this);
	}

	public void fireGameEvent(IGameEvent event) {
		if (!triggers.containsKey(event.getEventType())) {
			return;
		}
		for (SpellTrigger trigger : getListSnapshot(event.getEventType(), triggers)) {
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

	public void checkForSecrets(IGameEvent event) {
		if (!secrets.containsKey(event.getEventType())) {
			return;
		}
		for (SpellTrigger secret : getListSnapshot(event.getEventType(), secrets)) {
			// we need to double check here if the trigger still exists;
			if (secrets.get(event.getEventType()).contains(secret)) {
				secret.onGameEvent(event);
			}

			// secrets are always expired after one use
			secrets.get(event.getEventType()).remove(secret);
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
