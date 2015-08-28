package net.demilich.metastone.game.behaviour.neutralnetwork;

import java.util.Random;

public class HiddenUnit implements Unit {

	// serailver
	private static final long serialVersionUID = -6987443254721148687L;

	// the array of input weights
	public double[] weights;

	// the list of inputs to this hidden unit
	protected Unit[] units;

	// the current unit value (cached)
	protected double value;

	/**
	 * Builds a hidden unit taking the provided number of inputs. Sets the
	 * initial weights to be a copy of the provided weights
	 *
	 * @param units
	 *            The input units to this unit
	 * @param weights
	 *            The weights to use
	 */
	protected HiddenUnit(Unit[] units, double[] weights) {
		this.units = units;
		this.weights = new double[weights.length];

		System.arraycopy(weights, 0, this.weights, 0, weights.length);
	}

	/**
	 * Builds a hidden unit taking the provided number of inputs. Sets the
	 * initial weights to be random values to be 0.
	 *
	 * @param units
	 *            The input units to this unit
	 * @param random
	 *            The random number generator
	 */
	protected HiddenUnit(Unit[] units, Random random) {
		this.units = units;
		this.weights = new double[units.length];
	}

	/**
	 * Returns the sum of all of the inputs and weights
	 *
	 * @return the sum
	 */
	protected double getSum() {
		double total = 0;

		for (int i = 0; i < units.length; i++)
			total += weights[i] * units[i].getValue();

		return total;
	}

	/**
	 * Returns the current value of this input
	 *
	 * @return The current value of this input
	 */
	public double getValue() {
		return value;
	}

	/**
	 * Generates a new weight
	 *
	 * @param random
	 *            The rng
	 * @return A new value
	 */
	public void randomizeWeights(Random random) {
		for (int i = 0; i < weights.length; i++)
			weights[i] = random.nextDouble() * 0.2;
	}

	/**
	 * Recomputes the value of this hidden unit, querying it's prior inputs.
	 */
	public void recompute() {
		value = sigmoid(getSum());
	}

	/**
	 * Implements the sigmoid function to provide the non-linearity to this
	 * function. Simply returns
	 *
	 * 1 / (1 + e^-x)
	 *
	 * @param x
	 *            The value to apply the function to
	 * @return The result
	 */
	protected double sigmoid(double x) {
		return (1 / (1 + Math.exp(-x)));
	}
}
