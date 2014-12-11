package net.pferdimanzug.hearthstone.analyzer.game.behaviour.threat.cuckoo;

import net.pferdimanzug.hearthstone.analyzer.game.behaviour.threat.FeatureVector;

public interface IFitnessFunction {
	
	double evaluate(FeatureVector featureVector);

}
