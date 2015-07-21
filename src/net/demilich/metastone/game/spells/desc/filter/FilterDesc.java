package net.demilich.metastone.game.spells.desc.filter;

import java.lang.reflect.InvocationTargetException;
import java.util.EnumMap;
import java.util.Map;

import net.demilich.metastone.game.cards.desc.Desc;

public class FilterDesc extends Desc<FilterArg> {

	public FilterDesc(Map<FilterArg, Object> arguments) {
		super(arguments);
	}

	public static Map<FilterArg, Object> build(Class<? extends EntityFilter> filterClass) {
		final Map<FilterArg, Object> arguments = new EnumMap<>(FilterArg.class);
		arguments.put(FilterArg.CLASS, filterClass);
		return arguments;
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

	@SuppressWarnings("unchecked")
	public Class<? extends EntityFilter> getFilterClass() {
		return (Class<? extends EntityFilter>) get(FilterArg.CLASS);
	}

}
