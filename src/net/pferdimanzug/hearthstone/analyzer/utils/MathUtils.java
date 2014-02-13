package net.pferdimanzug.hearthstone.analyzer.utils;

public class MathUtils {
	
	private MathUtils() {}
	
	public static int clamp(int value, int min, int max) {
		if (value < min) {
			return min;
		} else if (value > max) {
			return max;
		}
		return value;
	}

}
