package net.pferdimanzug.hearthstone.analyzer.game.behaviour.threat.ga;

import net.pferdimanzug.hearthstone.analyzer.game.behaviour.threat.FeatureVector;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.threat.WeightedFeature;
import net.pferdimanzug.hearthstone.analyzer.game.decks.Deck;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.DoubleGene;

public class GeneticFeatureOptimizer {
	private Genotype genotype;

	public void init(Deck deck1, Deck deck2) {
		Configuration gaConf = new DefaultConfiguration();
		gaConf.setPreservFittestIndividual(true);

		try {
			IChromosome sampleChromosome = new Chromosome(gaConf, toGenes(FeatureVector.getDefault(), gaConf));
			gaConf.setSampleChromosome(sampleChromosome);
			gaConf.setPopulationSize(5);
			gaConf.setFitnessFunction(new WinRateFitnessFunction(deck1, deck2));
			genotype = Genotype.randomInitialGenotype(gaConf);
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public void train() {
		genotype.evolve();
	}
	
	public FeatureVector getBest() {
		IChromosome fittest = genotype.getFittestChromosome();
		System.out.println("Fitness value: " + fittest.getFitnessValue());
		return toVector(fittest);
	}
	
	public static FeatureVector toVector(IChromosome chromosome) {
		FeatureVector vector = new FeatureVector();
		WeightedFeature[] values = WeightedFeature.values();
		for (int i = 0; i < values.length; i++) {
			WeightedFeature feature = values[i];
			double value = (double) chromosome.getGene(i).getAllele();
			vector.set(feature, value);
		}
		return vector;
	}
	
	public static Gene[] toGenes(FeatureVector featureVector, Configuration configuration) {
		Gene[] genes = new Gene[featureVector.getValues().size()];
		int i = 0;
		for (Double weight : featureVector.getValues().values()) {
			try {
				genes[i] = new DoubleGene(configuration, -100, 100);
				genes[i++].setAllele(weight);
			} catch (InvalidConfigurationException e) {
				e.printStackTrace();
			}
		}
		return genes;
	}

}
