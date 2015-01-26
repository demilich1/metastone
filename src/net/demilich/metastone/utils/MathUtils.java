package net.demilich.metastone.utils;

public class MathUtils {

	public static double clamp(double value, double min, double max) {
		if (value < min) {
			return min;
		} else if (value > max) {
			return max;
		}
		return value;
	}

	public static int clamp(int value, int min, int max) {
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

	public static double levy(double xMin, double alpha) {
		double u = Math.random();
		return xMin * Math.pow(u, -1 / alpha);
	}

	private MathUtils() {
	}

}
