package com.hiddenswitch.cardsgen.functions;

import net.demilich.metastone.game.statistics.SimulationResult;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.ReduceFunction;

public class MergeSimulationResults implements Function2<SimulationResult, SimulationResult, SimulationResult> {
	@Override
	public SimulationResult call(SimulationResult v1, SimulationResult v2) throws Exception {
		return v1.merge(v2);
	}
}
