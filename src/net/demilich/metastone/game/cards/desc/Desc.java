package net.demilich.metastone.game.cards.desc;

import java.util.Map;

public class Desc<T> {

	private final Map<T, Object> arguments;

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
}
