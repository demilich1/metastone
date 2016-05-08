package net.demilich.metastone.trainingmode;

import net.demilich.metastone.game.behaviour.threat.FeatureVector;

public class TrainingData {

	private final String deckName;
	private final FeatureVector featureVector;

	public TrainingData(String deckName, FeatureVector featureVector) {
		this.deckName = deckName;
		this.featureVector = featureVector;
	}

	public String getDeckName() {
		return deckName;
	}

	public FeatureVector getFeatureVector() {
		return featureVector;
	}

}
