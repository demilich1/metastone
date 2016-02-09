package net.demilich.metastone.game.spells.desc.trigger;

import java.lang.reflect.InvocationTargetException;
import java.util.EnumMap;
import java.util.Map;

import net.demilich.metastone.game.cards.desc.Desc;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.trigger.GameEventTrigger;

public class EventTriggerDesc extends Desc<EventTriggerArg> {

	public static Map<EventTriggerArg, Object> build(Class<? extends GameEventTrigger> triggerClass) {
		final Map<EventTriggerArg, Object> arguments = new EnumMap<>(EventTriggerArg.class);
		arguments.put(EventTriggerArg.CLASS, triggerClass);
		return arguments;
	}

	public static EventTriggerDesc createEmpty(Class<? extends GameEventTrigger> triggerClass) {
		return new EventTriggerDesc(EventTriggerDesc.build(triggerClass));
	}

	public EventTriggerDesc(Map<EventTriggerArg, Object> arguments) {
		super(arguments);
	}

	public GameEventTrigger create() {
		Class<? extends GameEventTrigger> triggerClass = getTriggerClass();
		try {
			return triggerClass.getConstructor(EventTriggerDesc.class).newInstance(this);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

	public TargetPlayer getTargetPlayer() {
		return contains(EventTriggerArg.TARGET_PLAYER) ? (TargetPlayer) get(EventTriggerArg.TARGET_PLAYER) : TargetPlayer.SELF;
	}

	public TargetPlayer getSourcePlayer() {
		return contains(EventTriggerArg.SOURCE_PLAYER) ? (TargetPlayer) get(EventTriggerArg.SOURCE_PLAYER) : TargetPlayer.SELF;
	}

	@SuppressWarnings("unchecked")
	public Class<? extends GameEventTrigger> getTriggerClass() {
		return (Class<? extends GameEventTrigger>) get(EventTriggerArg.CLASS);
	}

}
