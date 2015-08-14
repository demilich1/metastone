package net.demilich.metastone.game.behaviour.neutralnetwork;

import java.io.Serializable;

public interface Unit extends Serializable {

	/**
	 * Returns the current value of this input
	 *
	 * @return The current value of this input
	 */
	public double getValue();

	/**
	 * Recomputes the value of this hidden unit, querying it's prior inputs.
	 */
	public void recompute();

}
