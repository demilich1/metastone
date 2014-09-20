package net.pferdimanzug.hearthstone.analyzer.utils;

public class MathUtils {

	public static int clamp(int value, int min, int max) {
		if (value < min) {
			return min;
		} else if (value > max) {
			return max;
		}
		return value;
	}

	public static double clamp(double value, double min, double max) {
		if (value < min) {
			return min;
		} else if (value > max) {
			return max;
		}
		return value;
	}

	public static double clamp01(double value) {
		return clamp(value, 0.0, 1.0);
	}

	private MathUtils() {
	}

}
