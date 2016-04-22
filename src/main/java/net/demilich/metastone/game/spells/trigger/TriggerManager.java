package net.demilich.metastone.game.spells.trigger;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.utils.IDisposable;

public class TriggerManager implements Cloneable, IDisposable {

	public static Logger logger = LoggerFactory.getLogger(TriggerManager.class);

	private final List<IGameEventListener> triggers = new ArrayList<IGameEventListener>();

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
			logger.warn("Warning, many triggers: " + triggers.size() + " adding one of type: " + trigger);
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
		List<IGameEventListener> eventTriggers = new ArrayList<IGameEventListener>();
		List<IGameEventListener> removeTriggers = new ArrayList<IGameEventListener>();
		for (IGameEventListener trigger : triggers) {
			// In order to stop premature expiration, check
			// for a oneTurnOnly tag and that it isn't delayed.
			if (event.getEventType() == GameEventType.TURN_END) {
				if(trigger.oneTurnOnly() && !trigger.isDelayed() &&
						!trigger.interestedIn(GameEventType.TURN_START) &&
						!trigger.interestedIn(GameEventType.TURN_END)) {
					trigger.expire();
				}
				trigger.delayTimeDown();
			}
			if (trigger.isExpired()) {
				removeTriggers.add(trigger);
			}
			if (trigger.getLayer() != event.getTriggerLayer()) {
				continue;
			}

			if (!trigger.interestedIn(event.getEventType())) {
				continue;
			}
			if (triggers.contains(trigger) && trigger.canFire(event)) {
				eventTriggers.add(trigger);
			}
		}

		for (IGameEventListener trigger : eventTriggers) {
			if (trigger.canFireCondition(event) && hostNotOnSideBoard(event, trigger.getHostReference())) {
				trigger.onGameEvent(event);
			}

			// we need to double check here if the trigger still exists;
			// after all, a previous trigger may have removed it (i.e. double
			// corruption)
			if (trigger.isExpired()) {
				removeTriggers.add(trigger);
			}
		}

		for (IGameEventListener trigger : removeTriggers) {
			triggers.remove(trigger);
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

	public void printCurrentTriggers() {
		for (IGameEventListener trigger : triggers) {
			System.out.println();
			System.out.println(trigger.toString());
			System.out.println();
		}
	}

	public void removeTrigger(IGameEventListener trigger) {
		if (!triggers.remove(trigger)) {
			System.out.println("Failed to remove trigger " + trigger);
		}
	}

	public void removeTriggersAssociatedWith(EntityReference entityReference) {
		for (IGameEventListener trigger : getListSnapshot(triggers)) {
			if (trigger.getHostReference().equals(entityReference)) {
				triggers.remove(trigger);
			}
		}
	}

	public boolean hostNotOnSideBoard(GameEvent event, EntityReference entityReference) {
		GameContext context = event.getGameContext();
		Entity entity = context.resolveSingleTarget(entityReference);
		if (context.getPlayer1().getSetAsideZone().contains(entity) || context.getPlayer2().getSetAsideZone().contains(entity)) {
			return false;
		}
		return true;
	}

}
