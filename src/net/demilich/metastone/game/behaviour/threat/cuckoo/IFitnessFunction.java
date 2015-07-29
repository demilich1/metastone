package net.demilich.metastone.game.behaviour.threat.cuckoo;

import net.demilich.metastone.game.behaviour.threat.FeatureVector;

public interface IFitnessFunction {

	double evaluate(FeatureVector featureVector);

}
