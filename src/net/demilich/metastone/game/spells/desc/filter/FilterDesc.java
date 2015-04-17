package net.demilich.metastone.game.spells.desc.filter;

import java.lang.reflect.InvocationTargetException;
import java.util.EnumMap;
import java.util.Map;

public class FilterDesc {
	
	public static Map<FilterArg, Object> build(Class<? extends EntityFilter> filterClass) {
		final Map<FilterArg, Object> arguments = new EnumMap<>(FilterArg.class);
		arguments.put(FilterArg.CLASS, filterClass);
		return arguments;
	}

	private final Map<FilterArg, Object> arguments;

	public FilterDesc(Map<FilterArg, Object> arguments) {
		this.arguments = arguments;
	}

	public boolean contains(FilterArg arg) {
		return arguments.containsKey(arg);
	}

	public EntityFilter create() {
		Class<? extends EntityFilter> filterClass = getFilterClass();
		try {
			return filterClass.getConstructor(FilterDesc.class).newInstance(this);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Object get(FilterArg arg) {
		return arguments.get(arg);
	}

	public boolean getBool(FilterArg arg) {
		return arguments.containsKey(arg) ? (boolean) get(arg) : false;
	}

	@SuppressWarnings("unchecked")
	public Class<? extends EntityFilter> getFilterClass() {
		return (Class<? extends EntityFilter>) arguments.get(FilterArg.CLASS);
	}


	public int getInt(FilterArg arg) {
		return arguments.containsKey(arg) ? (int) get(arg) : 0;
	}
}
