package net.pferdimanzug.hearthstone.analyzer.game.behaviour.threat.ga;

import java.util.List;
import java.util.Map;

import net.pferdimanzug.hearthstone.analyzer.game.behaviour.threat.FeatureVector;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.threat.WeightedFeature;
import net.pferdimanzug.hearthstone.analyzer.game.decks.Deck;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.Population;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.DoubleGene;

public class GeneticFeatureOptimizer {
	public static Gene[] toGenes(FeatureVector featureVector, Configuration configuration) {
		Gene[] genes = new Gene[featureVector.getValues().size()];
		Map<WeightedFeature, Double> values = featureVector.getValues();
		int i = 0;
		for (WeightedFeature feature : values.keySet()) {
			try {
				Double weight = values.get(feature);
				switch (feature) {
				case MINION_ATTACK_FACTOR:
				case MINION_DEFAULT_TAUNT_MODIFIER:
				case MINION_YELLOW_TAUNT_MODIFIER:
				case MINION_RED_TAUNT_MODIFIER:
				case MINION_DIVINE_SHIELD_MODIFIER:
				case MINION_INTRINSIC_VALUE:
					genes[i] = new DoubleGene(configuration, -10, 100);
					break;
				case MINION_HP_FACTOR:
					genes[i] = new DoubleGene(configuration, 0, 100);
					break;
				case MINION_SPELL_POWER_MODIFIER:
				case MINION_STEALTHED_MODIFIER:
				case MINION_UNTARGETABLE_BY_SPELLS_MODIFIER:
				case MINION_WINDFURY_MODIFIER:
					genes[i] = new DoubleGene(configuration, 0, 100);
					break;
				case OWN_CARD_COUNT:
				case OWN_HP_FACTOR:
				case OPPONENT_HP_FACTOR:
				case OPPONENT_CARD_COUNT:
					genes[i] = new DoubleGene(configuration, -100, 100);
					break;
				case RED_MODIFIER:
					genes[i] = new DoubleGene(configuration, -100, 0);
					break;
				case YELLOW_MODIFIER:
					genes[i] = new DoubleGene(configuration, -100, 10);
					break;
				default:
					break;
				}

				genes[i++].setAllele(weight);
			} catch (InvalidConfigurationException e) {
				e.printStackTrace();
			}
		}
		return genes;
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

	private Genotype genotype;

	public FeatureVector getBest() {
		IChromosome fittest = genotype.getFittestChromosome();
		System.out.println("Fitness value: " + fittest.getFitnessValue());
		return toVector(fittest);
	}

	public void init(List<Deck> decks) {
		Configuration gaConf = new DefaultConfiguration();
		gaConf.setPreservFittestIndividual(true);

		try {
			IChromosome sampleChromosome = new Chromosome(gaConf, toGenes(FeatureVector.getDefault(), gaConf));
			gaConf.setSampleChromosome(sampleChromosome);
			gaConf.setPopulationSize(50);
			gaConf.setFitnessFunction(new WinRateFitnessFunction(decks));
			genotype = new Genotype(gaConf, new Population(gaConf, sampleChromosome));
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	public void train() {
		genotype.evolve();
	}

}
