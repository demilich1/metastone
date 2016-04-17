package net.demilich.metastone.game.behaviour.neutralnetwork;

public class InputUnit implements Unit {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8699533068346744182L;
	// the current value of the input
	protected double value;

	/**
	 * Builds a hidden unit taking the provided number of inputs. Sets the
	 * initial weights to be random values, using the provided RNG.
	 *
	 * @param units
	 *            The input units to this unit
	 * @param random
	 *            The random number generator
	 */
	protected InputUnit() {
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
	 * Recomputes the value of this hidden unit, querying it's prior inputs.
	 */
	public void recompute() {
	}

	/**
	 * Sets the value of this input unit
	 *
	 * @return the value
	 */
	protected void setValue(double value) {
		this.value = value;
	}
}
