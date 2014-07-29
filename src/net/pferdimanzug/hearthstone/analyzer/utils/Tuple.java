package net.pferdimanzug.hearthstone.analyzer.utils;

public class Tuple<T> {

	private final T first;
	private final T second;

	public Tuple(T first, T second) {
		this.first = first;
		this.second = second;
	}

	public T getFirst() {
		return first;
	}

	public T getSecond() {
		return second;
	}
}
