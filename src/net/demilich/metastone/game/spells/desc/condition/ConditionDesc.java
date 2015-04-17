package net.demilich.metastone.game.spells.desc.condition;

import java.lang.reflect.InvocationTargetException;
import java.util.EnumMap;
import java.util.Map;

public class ConditionDesc {

	public static Map<ConditionArg, Object> build(Class<? extends Condition> conditionClass) {
		final Map<ConditionArg, Object> arguments = new EnumMap<>(ConditionArg.class);
		arguments.put(ConditionArg.CLASS, conditionClass);
		return arguments;
	}

	private final Map<ConditionArg, Object> arguments;

	public ConditionDesc(Map<ConditionArg, Object> arguments) {
		this.arguments = arguments;
	}

	public boolean contains(ConditionArg arg) {
		return arguments.containsKey(arg);
	}

	public Condition create() {
		Class<? extends Condition> valueProviderClass = getConditionClass();
		try {
			return valueProviderClass.getConstructor(ConditionDesc.class).newInstance(this);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Object get(ConditionArg arg) {
		return arguments.get(arg);
	}

	public boolean getBool(ConditionArg arg) {
		return arguments.containsKey(arg) ? (boolean) get(arg) : false;
	}

	@SuppressWarnings("unchecked")
	public Class<? extends Condition> getConditionClass() {
		return (Class<? extends Condition>) arguments.get(ConditionArg.CLASS);
	}

	public int getInt(ConditionArg arg) {
		return arguments.containsKey(arg) ? (int) get(arg) : 0;
	}

}
