package net.demilich.metastone.game.spells.desc.valueprovider;

import java.lang.reflect.InvocationTargetException;
import java.util.EnumMap;
import java.util.Map;

import net.demilich.metastone.game.targeting.EntityReference;

public class ValueProviderDesc {

	public static Map<ValueProviderArg, Object> build(Class<? extends ValueProvider> spellClass) {
		final Map<ValueProviderArg, Object> arguments = new EnumMap<>(ValueProviderArg.class);
		arguments.put(ValueProviderArg.CLASS, spellClass);
		return arguments;
	}

	private final Map<ValueProviderArg, Object> arguments;

	public ValueProviderDesc(Map<ValueProviderArg, Object> arguments) {
		this.arguments = arguments;
	}

	public boolean contains(ValueProviderArg arg) {
		return arguments.containsKey(arg);
	}

	public Object get(ValueProviderArg arg) {
		return arguments.get(arg);
	}

	public boolean getBool(ValueProviderArg arg) {
		return arguments.containsKey(arg) ? (boolean) get(arg) : false;
	}

	@SuppressWarnings("unchecked")
	public Class<? extends ValueProvider> getValueProviderClass() {
		return (Class<? extends ValueProvider>) arguments.get(ValueProviderArg.CLASS);
	}

	public int getInt(ValueProviderArg arg) {
		return arguments.containsKey(arg) ? (int) get(arg) : 0;
	}

	public EntityReference getSource() {
		return (EntityReference) get(ValueProviderArg.SOURCE);
	}

	public ValueProvider create() {
		Class<? extends ValueProvider> valueProviderClass = getValueProviderClass();
		try {
			return valueProviderClass.getConstructor(ValueProviderDesc.class).newInstance(this);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

}
