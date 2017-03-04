package net.demilich.metastone.game.cards.desc;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.valueprovider.ValueProvider;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Desc<T> implements Serializable {

	protected final Map<T, Object> arguments;

	public Desc(Map<T, Object> arguments) {
		this.arguments = arguments;
	}

	public boolean contains(T arg) {
		return arguments.containsKey(arg);
	}

	public Object get(T arg) {
		return arguments.get(arg);
	}

	public boolean getBool(T arg) {
		return arguments.containsKey(arg) ? (boolean) get(arg) : false;
	}

	public int getInt(T arg) {
		return arguments.containsKey(arg) ? (int) get(arg) : 0;
	}

	public String getString(T arg) {
		return arguments.containsKey(arg) ? (String) get(arg) : "";
	}

	public int getValue(T arg, GameContext context, Player player, Entity target, Entity host, int defaultValue) {
		Object storedValue = arguments.get(arg);
		if (storedValue == null) {
			return defaultValue;
		}
		if (storedValue instanceof ValueProvider) {
			ValueProvider valueProvider = (ValueProvider) storedValue;
			return valueProvider.getValue(context, player, target, host);
		}
		return (int) storedValue;
	}
}
