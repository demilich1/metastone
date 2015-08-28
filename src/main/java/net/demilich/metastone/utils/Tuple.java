package net.demilich.metastone.utils;

public class Tuple<T1, T2> {

	private final T1 first;
	private final T2 second;

	public Tuple(T1 first, T2 second) {
		this.first = first;
		this.second = second;
	}

	public T1 getFirst() {
		return first;
	}

	public T2 getSecond() {
		return second;
	}
}
