package net.demilich.metastone.game.behaviour.threat.cuckoo;

import net.demilich.metastone.game.behaviour.threat.FeatureVector;

public class CuckooAgent implements Comparable<CuckooAgent> {

	private FeatureVector data;
	private double fitness = -1;

	public CuckooAgent(FeatureVector data) {
		this.setData(data);
	}

	@Override
	public int compareTo(CuckooAgent other) {
		return Double.compare(other.getFitness(), getFitness());
	}

	public FeatureVector getData() {
		return data;
	}

	public double getFitness() {
		return fitness;
	}

	public void setData(FeatureVector data) {
		this.data = data;
	}

	public void setFitness(double fitness) {
		if (this.fitness < 0) {
			this.fitness = fitness;
		} else {
			this.fitness = (this.fitness + fitness) * 0.5;
		}
	}

}
