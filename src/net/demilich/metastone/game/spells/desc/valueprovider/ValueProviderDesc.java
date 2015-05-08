package net.demilich.metastone.game.spells.desc.valueprovider;

import java.lang.reflect.InvocationTargetException;
import java.util.EnumMap;
import java.util.Map;

import net.demilich.metastone.game.cards.desc.Desc;
import net.demilich.metastone.game.targeting.EntityReference;

public class ValueProviderDesc extends Desc<ValueProviderArg> {

	public static Map<ValueProviderArg, Object> build(Class<? extends ValueProvider> providerClass) {
		final Map<ValueProviderArg, Object> arguments = new EnumMap<>(ValueProviderArg.class);
		arguments.put(ValueProviderArg.CLASS, providerClass);
		return arguments;
	}


	public ValueProviderDesc(Map<ValueProviderArg, Object> arguments) {
		super(arguments);
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

	public EntityReference getSource() {
		return (EntityReference) get(ValueProviderArg.SOURCE);
	}

	@SuppressWarnings("unchecked")
	public Class<? extends ValueProvider> getValueProviderClass() {
		return (Class<? extends ValueProvider>) get(ValueProviderArg.CLASS);
	}

}
